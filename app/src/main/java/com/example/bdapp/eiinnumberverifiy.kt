package com.example.bdapp

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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

@OptIn(ExperimentalMaterial3Api::class)
class eiinnumberverifiy() {

    companion object {


        @Composable
        fun EnterEIIN(navController: NavController) {
            var eiin by rememberSaveable { mutableStateOf("") }
            var  toastContext = LocalContext.current
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                    Text(
                        text = "WE NEED TO VERIFIY",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Enter EIIN number to get OTP",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = eiin,
                        onValueChange = { eiin = it },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Place,
                                contentDescription = "EIIN NUBMER"
                            )
                        },
                        label = { Text("EIIN Number") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )

                    Button(onClick = {
                        if(eiin.isNotEmpty()){
                            val BASE_URL = "https://rest-api-vds-reader-production.up.railway.app/"
                            val apiService: ApiService by lazy {
                                Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                                    .create(ApiService::class.java)
                            }
                            val getStudentInfoCall: Call<List<Eiininfo>> =
                                apiService.geteiininfo(
                                    eiin.toString()
                                )

                            getStudentInfoCall.enqueue(object : Callback<List<Eiininfo>> {
                                override fun onResponse(
                                    call: Call<List<Eiininfo>>,
                                    response: Response<List<Eiininfo>>
                                ) {
                                    if (response.isSuccessful) {
                                        // Handle successful response
                                        val resultList: List<Eiininfo>? = response.body()
                                        if (resultList != null && resultList.isNotEmpty()) {
                                            val BASE_URL = "http://45.90.123.6:3000/"
                                            val apiService: ApiService by lazy {
                                                Retrofit.Builder()
                                                    .baseUrl(BASE_URL)
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build()
                                                    .create(ApiService::class.java)
                                            }
                                            MainActivity.requestParameters.mobile = resultList.first().phone


                                            val requestCall = apiService.requestOtp(MainActivity.requestParameters)


                                            requestCall.enqueue(object : Callback<ApiResponse> {
                                                override fun onResponse(
                                                    call: Call<ApiResponse>,
                                                    response: Response<ApiResponse>
                                                ) {
                                                    if (response.isSuccessful) {
                                                        val apiResponse = response.body()
                                                        if (apiResponse?.referenceNo!= null) {
                                                            Toast.makeText(toastContext,"OTP sent successfully",Toast.LENGTH_LONG).show()
                                                            Log.d("MyActivity", "OTP sent successfully: $apiResponse")
                                                            MainActivity.verifyParameters.referenceNo =
                                                                apiResponse?.referenceNo
                                                            navController.navigate("enterotp")
                                                        }
                                                        else {
                                                           Toast.makeText(toastContext,"Failed to send OTP",Toast.LENGTH_LONG).show()
                                                           //  Handle unsuccessful response
                                                            navController.navigate("enterotp")
                                                        }
                                                        Log.d("MyActivity", "OTP sent successfully: $apiResponse")
                                                    } else {
                                                        // Handle unsuccessful response
                                                        Toast.makeText(toastContext,"Failed to send OTP",Toast.LENGTH_LONG).show()
                                                    }
                                                }

                                                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                                                    // Handle failure
                                                    Toast.makeText(toastContext,"Network erro",Toast.LENGTH_LONG).show()
                                                  //  Log.e("MyActivity", "Network error: ${t.message}")
                                                }
                                            })

                                        }
                                    } else {
                                        Toast.makeText(toastContext,"Failed to Find",Toast.LENGTH_LONG).show()
                                        //
                                        // Handle unsuccessful response
                                        Log.e(
                                            "MyActivity",
                                            "Failed to Find: ${response.errorBody()?.string()}"
                                        )
                                    }

                                }

                                override fun onFailure(call: Call<List<Eiininfo>>, t: Throwable) {
                                    // Handle failure
                                    Toast.makeText(toastContext,"Network erro",Toast.LENGTH_LONG).show()
                                    Log.e("MyActivity", "Network error: ${t.message}")
                                }

                            })
                    }
                        else{
                            Toast.makeText(toastContext,"Please Insert All The Value",Toast.LENGTH_LONG).show()
                            Log.e("MyActivity","Please Insert All The Value")
                        }
                    },
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color.Gray),
                        shape = RoundedCornerShape(15.dp)

                    ) {
                        Text(text = "submit")
                    }
                }
            }


        }
    }
