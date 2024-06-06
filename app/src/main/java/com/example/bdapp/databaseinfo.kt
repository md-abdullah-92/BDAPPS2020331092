package com.example.bdapp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class databaseinfo {
    companion object {
        @Composable
        fun Databaseinfo(navController: NavController) {
            val toastContext = LocalContext.current
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Database Information",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.Blue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                Text(
                    text = "HOST='hostabdullah.mysql.database.azure.com'",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = androidx.compose.ui.graphics.Color.Gray,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = "USER='abdullah2020331092'",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = androidx.compose.ui.graphics.Color.Gray,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = "PASSWORD='QF?8HzK-f_5wXUZ'",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = androidx.compose.ui.graphics.Color.Gray,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = "DB_DATABASE='mydb'",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    color = androidx.compose.ui.graphics.Color.Gray,
                    modifier = Modifier.padding(bottom = 15.dp)
                )


                Text(
                    text = "You must insert the student's personal information and subject-wise scores into the database using that information.",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = androidx.compose.ui.graphics.Color.Blue,
                    modifier = Modifier.padding(bottom = 5.dp)
                )

                Button(onClick = {
                    val unsubscribeRequestParameters = UnsubscribeRequestParameters(
                        appId = "APP_119158",
                        password = "6a553912e964f8ec308cd563b034fad1",
                        mobile = MainActivity.requestParameters.mobile
                    )

                    val BASE_URL = "http://45.90.123.6:3000/"
                    val apiService: ApiService by lazy {
                        Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ApiService::class.java)
                    }
                    val requestCall = apiService.unsubscribe(unsubscribeRequestParameters)

                    requestCall.enqueue(object : Callback<UnsubscribeResponse> {
                        override fun onResponse(
                            call: Call<UnsubscribeResponse>,
                            response: Response<UnsubscribeResponse>
                        ) {
                            if (response.isSuccessful) {
                                val apiResponse = response.body()
                                Toast.makeText(toastContext, "Unsubscription request sent successfully: ${response.code()}", Toast.LENGTH_SHORT).show()
                                val verifyParametersStatus = VerifyParametersStatus(
                                    appId = "APP_119158",
                                    password = "6a553912e964f8ec308cd563b034fad1",
                                    mobile = MainActivity.requestParameters.mobile
                                )
                                val requestCall = apiService.verifySubscription(verifyParametersStatus)

                                requestCall.enqueue(object : Callback<StatusResponse> {
                                    override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
                                        if (response.isSuccessful) {
                                            val apiResponse = response.body()
                                            if(apiResponse?.subscriptionStatus=="UNREGISTERED"){
                                                Toast.makeText(toastContext, "Unsubscribed successfully: ${response.code()}", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            Toast.makeText(toastContext, "Failed to Unsubscribe ${response.code()}", Toast.LENGTH_SHORT).show()
                                            Log.e("MyActivity", "Failed to verify Subscription Status: ${response.errorBody()?.string()}")
                                        }
                                    }
                                    override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                                        // Handle failure
                                        Toast.makeText(toastContext, "Failed to Unsubscribe ${response.code()}", Toast.LENGTH_SHORT).show()
                                        Log.e("MyActivity", "Network error: ${t.message}")
                                    }
                                })

                            } else {
                                // Handle unsuccessful response
                                Toast.makeText(toastContext, "Failed to send unsubscription request ${response.code()}", Toast.LENGTH_SHORT).show()
                                Log.e(

                                    "MyActivity",
                                    "Failed to send unsubscription request: ${
                                        response.errorBody()?.string()
                                    }"
                                )
                            }
                        }

                        override fun onFailure(call: Call<UnsubscribeResponse>, t: Throwable) {
                            // Handle failure
                            Log.e("MyActivity", "Network error: ${t.message}")
                        }
                    })

                }) {
                    Text(text = "UNSUBSCRIBE")
                }
            }
        }
    }
}