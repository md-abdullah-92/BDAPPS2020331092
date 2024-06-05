package com.example.bdapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Resultpage {
    companion object {
        @Composable
        fun ResultPage(navController: NavController) {
            val toastContext = LocalContext.current
            val BASE_URL = "https://rest-api-vds-reader-production.up.railway.app/"
            val apiService: ApiService by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.sustpic),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize()
                )
            }
            Column {
                Spacer(modifier = Modifier.height(230.dp))
                Card(
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(1.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(alpha = .5f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    //  SubmitPage.Textrow1("Semester Wise Result", 15.dp)
                    DropDownBox(navController)
                    // SubmitPage.Textrow1("All Semesters GradeSheet", 15.dp)
                    Button(
                        onClick = {
                            val regNo = MainActivity.studentInfo?.reg_no.toString()
                            val eiin = MainActivity.studentInfo?.eiin.toString()

                            if (regNo.isNotEmpty()) {
                                val call: Call<List<Getdata>> = apiService.getStudentFullResults(regNo.toInt(),eiin.toString())
                                call.enqueue(object : Callback<List<Getdata>> {
                                    override fun onResponse(
                                        call: Call<List<Getdata>>,
                                        response: Response<List<Getdata>>
                                    ) {
                                        if (response.isSuccessful) {
                                            // Handle successful response
                                            val resultList: List<Getdata>? = response.body()
                                            if (resultList != null && resultList.isNotEmpty()) {
                                                MainActivity.resultList = resultList
                                                navController.navigate("fullresult")
                                            } else {
                                                // Handle the case where the response body is empty or null
                                                // You might want to show an error message or take appropriate action
                                            }
                                        } else {
                                            // Handle unsuccessful response
                                            Toast.makeText(
                                                toastContext,
                                                "API call failed. Code: ${response.code()}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        // Always navigate to "resultpage" whether the API call was successful or not
                                    }

                                    override fun onFailure(
                                        call: Call<List<Getdata>>,
                                        t: Throwable
                                    ) {
                                        // Handle network failure
                                        // You might want to show an error message or take appropriate action
                                        Toast.makeText(
                                            toastContext,
                                            "Network failure. Error: ${t.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                            } else {
                                Toast.makeText(
                                    toastContext,
                                    "Please Insert All The Value",
                                    Toast.LENGTH_SHORT
                                ).show()}
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(30.dp)
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(Color.White),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text(
                            text = "GradeSheet",
                            color = Color.Black,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    //ubmitPage.Textrow1("Certificate", 15.dp)
                    Spacer(modifier = Modifier.height(10.dp))

                }
            }
        }


        @Composable
        fun DropDownBox(navController: NavController) {
            val BASE_URL = "https://rest-api-vds-reader-production.up.railway.app/"
            val apiService: ApiService by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }
            val toastContext = LocalContext.current
            var expanded = remember { mutableStateOf(false) }


            Card(
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(1.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .width(30.dp)
                    .padding(16.dp)
                    .clickable { expanded.value = !expanded.value }
            )
            {
                Text(
                    "Semester Wise Result",
                    Modifier.padding(15.dp)
                )
            }


            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier.padding(16.dp)
            ) {
                // Create DropdownMenuItems for each option
                for (semester in 1..8) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "$semester${getOrdinalSuffix(semester)} Semester")
                        },
                        onClick = {
                            val regNo = MainActivity.studentInfo?.reg_no.toString()
                            val eiin = MainActivity.studentInfo?.eiin.toString()
                            if (regNo.isNotEmpty()) {
                                getResultsForSemester(navController, toastContext, regNo, semester,eiin)
                            } else {
                                showToast(toastContext, "Please Insert All The Value")
                            }
                        }
                    )
                }
            }
        }
    }
}
private fun getOrdinalSuffix(number: Int): String {
    val suffixes = listOf("","st", "nd", "rd") + List(7) { "th" }
    return suffixes[number]
}
private fun getResultsForSemester(
    navController: NavController,
    toastContext: Context,
    regNo: String,
    semester: Int,
    eiin:String
) {
    val BASE_URL = "https://rest-api-vds-reader-production.up.railway.app/"
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val semesterWithSuffix = "$semester${getOrdinalSuffix(semester)}"
    val call: Call<List<Getdata>> = apiService.getResults(semesterWithSuffix, regNo.toInt(),eiin)

    call.enqueue(object : Callback<List<Getdata>> {
        override fun onResponse(call: Call<List<Getdata>>, response: Response<List<Getdata>>) {
            if (response.isSuccessful) {
                val resultList: List<Getdata>? = response.body()
                if (resultList != null && resultList.isNotEmpty()) {
                    MainActivity.resultList = resultList
                    MainActivity.semester = semesterWithSuffix
                    navController.navigate("semesterresult")
                } else {
                    showToast(toastContext, "No data found for $semesterWithSuffix semester")
                }
            } else {
                showToast(toastContext, "API call failed. Code: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<List<Getdata>>, t: Throwable) {
            showToast(toastContext, "Network failure. Error: ${t.message}")
        }
    })
}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
