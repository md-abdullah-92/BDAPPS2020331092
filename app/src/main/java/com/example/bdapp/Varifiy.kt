package com.example.bdapp

import android.util.Log
import com.example.bdapp.MainActivity.DataManager.verifyParameters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Varifiy {


      fun verify(otp: String) {
        val BASE_URL = "http://45.90.123.6:3000/"
        val apiService: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
        verifyParameters.otp = otp
        Log.d("MyActivity", "${verifyParameters}")
        val requestCall = apiService.verifyOtp(verifyParameters)

        requestCall.enqueue(object : Callback<OtpVerifyRespone> {
            override fun onResponse(
                call: Call<OtpVerifyRespone>,
                response: Response<OtpVerifyRespone>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("MyActivity", "OTP verified successfully: $apiResponse")
                } else {
                    // Handle unsuccessful response
                    Log.e("MyActivity", "Failed to verify OTP: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<OtpVerifyRespone>, t: Throwable) {
                // Handle failure
                Log.e("MyActivity", "Network error: ${t.message}")
            }
        })
    }

    fun verifyStatus() {
        val BASE_URL = "http://45.90.123.6:3000/"
        val apiService: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
        val verifyParametersStatus = VerifyParametersStatus(
            appId = "APP_11883",
            password = "cab1c32cdbe7b1489ec6048e33296a43",
            mobile = "8801603252292"
        )
        val requestCall = apiService.verifySubscription(verifyParametersStatus)

        requestCall.enqueue(object : Callback<StatusResponse> {
            override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("MyActivity", "Subscription Status verified successfully: $apiResponse")
                } else {
                    // Handle unsuccessful response
                    Log.e("MyActivity", "Failed to verify Subscription Status: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                // Handle failure
                Log.e("MyActivity", "Network error: ${t.message}")
            }
        })
    }
}