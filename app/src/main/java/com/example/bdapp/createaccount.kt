package com.example.bdapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bdapp.MainActivity.DataManager.p
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class createaccount {
    companion object {
        @Composable
        fun Createaccount(navController: NavController) {
            var phoneNumber by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }
            val toastContext = LocalContext.current

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "SET PASSWORD",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Enter password ",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Password"
                        )
                    },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                Text(
                    text = "Re-Enter password ",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Re-type Password"
                        )
                    },
                    label = { Text("Retype Password") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                Button(
                    onClick = { handleButtonClick(phoneNumber, password, toastContext, navController) },
                    modifier = Modifier.width(130.dp)
                ) {
                    Text(text = "Submit")
                }
            }
        }

        private fun handleButtonClick(phoneNumber: String, password: String, context: Context, navController: NavController) {
            if (phoneNumber.isNotEmpty() && password.isNotEmpty()) {
                val apiService = createApiService("https://rest-api-vds-reader-production.up.railway.app/")
                MainActivity.Create?.password = password
                val phone = MainActivity.requestParameters.mobile

                val requestCall = apiService.createid(create(eiin = MainActivity.Create.eiin, phone=phone, password=password))

                requestCall.enqueue(object : Callback<create> {
                    override fun onResponse(call: Call<create>, response: Response<create>) {
                        if (response.isSuccessful) {
                            handleSubscriptionStatus(context, navController)
                        } else {
                            Toast.makeText(context, "Failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                            Log.e("MyActivity", "Failed to create account: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<create>, t: Throwable) {
                        Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                        Log.e("MyActivity", "Network error: ${t.message}")
                    }
                })
            } else {
                Toast.makeText(context, "Please enter both phone number and password", Toast.LENGTH_LONG).show()
                Log.e("MyActivity", "Please enter both phone number and password")
            }
        }

        private fun handleSubscriptionStatus(context: Context, navController: NavController) {
            val apiService = createApiService("http://45.90.123.6:3000/")
            val verifyParametersStatus = VerifyParametersStatus(
                appId = "APP_119158",
                password = "6a553912e964f8ec308cd563b034fad1",
                mobile = MainActivity.requestParameters.mobile
            )
            Log.e("M",MainActivity.requestParameters.mobile)
            val requestCall = apiService.verifySubscription(verifyParametersStatus)

            requestCall.enqueue(object : Callback<StatusResponse> {
                override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Toast.makeText(context, "Subscription Status verified successfully", Toast.LENGTH_LONG).show()
                        if (apiResponse?.subscriptionStatus != "REGISTERED") {
                            navController.navigate("subscribe")
                        } else {
                            navController.navigate("unsubscribe")
                        }
                    } else {
                        Toast.makeText(context, "Failed to verify Subscription Status", Toast.LENGTH_LONG).show()
                        Log.e("MyActivity", "Failed to verify Subscription Status: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                    Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_LONG).show()
                    Log.e("MyActivity", "Network error: ${t.message}")
                }
            })
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
