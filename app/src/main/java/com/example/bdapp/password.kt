package com.example.bdapp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
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

class password {
    companion object {
        private const val TAG = "PasswordVerify"

        @Composable
        fun Password(navController: NavController) {
            var phoneNumber by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }
            val context = LocalContext.current

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
                    text = "Enter Phone number and password",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Phone,
                            contentDescription = "Phone Number"
                        )
                    },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Password"
                        )
                    },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                Button(
                    onClick = {
                        if (password.isNotEmpty() && phoneNumber.isNotEmpty() && MainActivity.Create?.password == password && MainActivity.Create?.phone == phoneNumber) {
                            MainActivity.Create?.password = password
                            val NEW_URL = "http://45.90.123.6:3000/"

                            val apiService: ApiService by lazy {
                                Retrofit.Builder()
                                    .baseUrl(NEW_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                                    .create(ApiService::class.java)
                            }
                            val verifyParametersStatus = VerifyParametersStatus(
                                appId = "APP_119158",
                                password = "6a553912e964f8ec308cd563b034fad1",
                                mobile = MainActivity.requestParameters.mobile
                            )
                            val verifyRequestCall = apiService.verifySubscription(verifyParametersStatus)

                            verifyRequestCall.enqueue(object : Callback<StatusResponse> {
                                override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
                                    if (response.isSuccessful) {
                                        val apiResponse = response.body()
                                        Toast.makeText(context, "Subscription Status verified successfully", Toast.LENGTH_LONG).show()
                                        if (apiResponse?.subscriptionStatus != "REGISTERED") {
                                            navController.navigate("subscribe")
                                        } else {
                                            navController.navigate("unsubscribe")
                                        }
                                        Log.d(TAG, "Subscription Status verified successfully: $apiResponse")
                                    } else {
                                        val errorBody = response.errorBody()?.string()
                                        Toast.makeText(context, "Failed to verify Subscription Status", Toast.LENGTH_LONG).show()
                                        Log.e(TAG, "Failed to verify Subscription Status: $errorBody")
                                    }
                                }

                                override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                                    Toast.makeText(context, "Network error", Toast.LENGTH_LONG).show()
                                    Log.e(TAG, "Network error: ${t.message}")
                                }
                            })

                        } else {
                            Toast.makeText(context, "Please enter the correct phone number and password", Toast.LENGTH_LONG).show()
                            Log.e(TAG, "Phone number or password is incorrect")
                        }
                    },
                    modifier = Modifier.width(130.dp)
                ) {
                    Text(text = "Login")
                }
            }
        }
    }
}
