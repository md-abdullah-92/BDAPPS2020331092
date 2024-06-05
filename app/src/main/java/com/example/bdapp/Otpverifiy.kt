package com.example.bdapp

//import androidx.compose.material3.OutlinedTextFieldDefaults
//import com.example.abartry.RetrofitStuffs.MyApiService
//import com.example.abartry.RetrofitStuffs.ServiceBuilder
//import com.example.abartry.data.ApiResponse
//import com.example.abartry.data.RequestParameters
//import com.example.abartry.ui.theme.AbarTryTheme
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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

class Otpverifiy {
    companion object {
        @Composable
        fun EnterOTP(navController: NavController) {
            val toastContext = LocalContext.current
            var value by rememberSaveable {
                mutableStateOf("");
            }
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
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Button(onClick = {
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
                                    Toast.makeText(toastContext,"OTP verified successfully", Toast.LENGTH_LONG).show()
                                   // Log.d("MyActivity", "OTP verified successfully: $apiResponse")
                                    val BASE_URL = "http://45.90.123.6:3000/"
                                    val apiService: ApiService by lazy {
                                        Retrofit.Builder()
                                            .baseUrl(BASE_URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build()
                                            .create(ApiService::class.java)
                                    }
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
                                                Toast.makeText(toastContext,"Subscription Status verified successfully", Toast.LENGTH_LONG).show()
                                                if(apiResponse?.subscriptionStatus!="REGISTERED"){
                                                    Log.e("MyActivity", "Failed to verify Subscription Status: ${response.errorBody()?.string()}")
                                                    navController.navigate("subscribe")
                                                }
                                                else{
                                                    Log.e("MyActivity", "Failed to verify Subscription Status: ${response.errorBody()?.string()}")
                                                    navController.navigate("unsubscribe")
                                                }
                                               // Toast.makeText(toastContext,"Subscription Status verified successfully", Toast.LENGTH_LONG).show()
                                                Log.d("MyActivity", "Subscription Status verified successfully: $apiResponse")
                                            } else {
                                                val apiResponse = response.body()
                                                Toast.makeText(toastContext,"Subscription Status verified successfully", Toast.LENGTH_LONG).show()
                                                if(apiResponse?.subscriptionStatus!="REGISTERED"){
                                                    navController.navigate("subscribe")
                                                }
                                                else{
                                                    navController.navigate("unsubscribe")
                                                }
                                                //

                                                Toast.makeText(toastContext,"Failed to verify Subscription Status", Toast.LENGTH_LONG).show()// Handle unsuccessful response
                                                Log.e("MyActivity", "Failed to verify Subscription Status: ${response.errorBody()?.string()}")
                                            }
                                        }
                                        override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                                            // Handle failure

                                            Toast.makeText(toastContext,"Network error", Toast.LENGTH_LONG).show()
                                            Log.e("MyActivity", "Network error: ${t.message}")
                                        }
                                    })

                                } else {

                                    Toast.makeText(toastContext,"Failed to verify OTP", Toast.LENGTH_LONG).show()// Handle unsuccessful response
                                    Log.e("MyActivity", "Failed to verify OTP: ${response.errorBody()?.string()}")
                                }
                            }
                            override fun onFailure(call: Call<OtpVerifyRespone>, t: Throwable) {

                                Toast.makeText(toastContext,"Network error", Toast.LENGTH_LONG).show()// Handle failure

                                Log.e("MyActivity", "Network error: ${t.message}")
                            }
                        })
                    }, modifier = Modifier.width(130.dp)) {
                        Text(text = "Verify")
                    }
                }
            }
        }
    }
}