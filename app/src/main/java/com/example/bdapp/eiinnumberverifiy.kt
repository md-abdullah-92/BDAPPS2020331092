package com.example.bdapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
class eiinnumberverifiy {

    companion object {
        private const val TAG = "EiinNumberVerify"

        @Composable
        fun EnterEIIN(navController: NavController) {
            var eiin by rememberSaveable { mutableStateOf("") }
            val toastContext = LocalContext.current

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "WE NEED TO VERIFY",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Enter EIIN number to get OTP Or Login :-",
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
                            contentDescription = "EIIN NUMBER"
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

                Button(
                    onClick = { handleButtonClick(eiin, toastContext, navController) },
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(text = "Submit")
                }
            }
        }

        private fun handleButtonClick(eiin: String, context: Context, navController: NavController) {
            Log.d(TAG, "Button clicked with EIIN: $eiin")
            if (eiin.isNotEmpty()) {
                val apiService = createApiService("https://rest-api-vds-reader-production.up.railway.app/")
                Log.d(TAG, "Fetching EIIN info for: $eiin")
                apiService.geteiininfo(eiin).enqueue(object : Callback<List<Eiininfo>> {
                    override fun onResponse(call: Call<List<Eiininfo>>, response: Response<List<Eiininfo>>) {
                        if (response.isSuccessful) {
                            val resultList = response.body()
                            Log.d(TAG, "EIIN info fetched successfully: $resultList")
                            if (resultList != null && resultList.isNotEmpty()) {
                                val phone = resultList.first().phone
                                MainActivity.requestParameters.mobile = phone
                                MainActivity.Create?.eiin = eiin
                                MainActivity.Create?.phone = phone.toString()

                                Log.d(TAG, "Creating info for EIIN: $eiin with phone: $phone")
                                apiService.createinfo(eiin).enqueue(object : Callback<List<create>> {
                                    override fun onResponse(call: Call<List<create>>, response: Response<List<create>>) {
                                        if (response.isSuccessful) {
                                            val apiResponse = response.body()
                                            Log.d(TAG, "Info created successfully: $apiResponse")
                                            if (apiResponse != null && apiResponse.isNotEmpty()) {
                                                val firstRecord = apiResponse.firstOrNull()
                                                if (firstRecord?.password != null) {
                                                    MainActivity.Create = firstRecord
                                                    navController.navigate("password")
                                                    Log.d(TAG, "Password available, navigating to password screen")
                                                }
                                            }
                                        } else {
                                            Log.e(TAG, "Failed to create info: ${response.errorBody()?.string()}")
                                            handleCreateInfoError(response, context, navController)
                                        }
                                    }

                                    override fun onFailure(call: Call<List<create>>, t: Throwable) {
                                        Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show()
                                        Log.e(TAG, "Network error: ${t.message}")
                                    }
                                })
                            } else {
                                Toast.makeText(context, "Failed to find EIIN", Toast.LENGTH_LONG).show()
                                Log.e(TAG, "Failed to find EIIN: ${response.errorBody()?.string()}")
                            }
                        } else {
                            Toast.makeText(context, "Failed to find EIIN", Toast.LENGTH_LONG).show()
                            Log.e(TAG, "Failed to find EIIN: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<List<Eiininfo>>, t: Throwable) {
                        Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show()
                        Log.e(TAG, "Network error: ${t.message}")
                    }
                })
            } else {
                Toast.makeText(context, "Please insert the EIIN number", Toast.LENGTH_LONG).show()
                Log.e(TAG, "EIIN number is empty")
            }
        }

        private fun handleCreateInfoError(response: Response<List<create>>, context: Context, navController: NavController) {
            Log.e(TAG, "Error creating info: ${response.errorBody()?.string()}")
            if (response.code() == 404) {
                val apiService = createApiService("http://45.90.123.6:3000/")
                Log.d(TAG, "Requesting OTP")
                apiService.requestOtp(MainActivity.requestParameters).enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            val apiResponse = response.body()
                            if (apiResponse?.referenceNo != null) {
                                Toast.makeText(context, "OTP sent successfully", Toast.LENGTH_LONG).show()
                                Log.d(TAG, "OTP sent successfully: $apiResponse")
                                MainActivity.verifyParameters.referenceNo = apiResponse.referenceNo
                                navController.navigate("enterotp")
                            } else {
                                Toast.makeText(context, "Failed to send OTP", Toast.LENGTH_LONG).show()
                                navController.navigate("enterotp")
                            }
                        } else {
                            Toast.makeText(context, "Failed to send OTP", Toast.LENGTH_LONG).show()
                            Log.e(TAG, "Failed to send OTP: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show()
                        Log.e(TAG, "Network error: ${t.message}")
                    }
                })
            } else {
                Toast.makeText(context, "Failed to create info", Toast.LENGTH_LONG).show()
                Log.e(TAG, "Failed to create info: ${response.errorBody()?.string()}")
            }
        }

        private fun createApiService(baseUrl: String): ApiService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
