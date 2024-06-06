package com.example.bdapp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Otpverifiy {
    companion object {
        private const val TAG = "OtpVerify"

        @Composable
        fun EnterOTP(navController: NavController) {
            val toastContext = LocalContext.current
            var value by rememberSaveable { mutableStateOf("") }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Enter the OTP sent in academic phone number",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = value,
                    onValueChange = { value = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Phone number"
                        )
                    },
                    label = { Text("Enter OTP") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth(9 / 11f)
                        .padding(10.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            val BASE_URL = "http://45.90.123.6:3000/"
                            val apiService: ApiService by lazy {
                                Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                                    .create(ApiService::class.java)
                            }
                            MainActivity.verifyParameters.otp = value
                            val requestCall = apiService.verifyOtp(MainActivity.verifyParameters)

                            requestCall.enqueue(object : Callback<OtpVerifyRespone> {
                                override fun onResponse(
                                    call: Call<OtpVerifyRespone>,
                                    response: Response<OtpVerifyRespone>
                                ) {
                                    if (response.isSuccessful) {
                                        val apiResponse = response.body()
                                        Log.d(TAG, "OTP verified successfully: $apiResponse")
                                        navController.navigate("createaccount")
                                    } else {
                                        val errorBody = response.errorBody()?.string()
                                        Toast.makeText(toastContext, "Failed to verify OTP", Toast.LENGTH_LONG).show()
                                        Log.e(TAG, "Failed to verify OTP: $errorBody")
                                    }
                                }

                                override fun onFailure(call: Call<OtpVerifyRespone>, t: Throwable) {
                                    Toast.makeText(toastContext, "Network error", Toast.LENGTH_LONG).show()
                                    Log.e(TAG, "Network error: ${t.message}")
                                }
                            })
                        },
                        modifier = Modifier.width(130.dp)
                    ) {
                        Text(text = "Verify")
                    }
                }
            }
        }
    }
}
