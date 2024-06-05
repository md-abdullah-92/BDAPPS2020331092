package com.example.bdapp

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class subcription {
    companion object fun subscriptionOn(phone : String) {
        val subscribeRequestParameters = SubscribeRequestParameters(
            appId = "APP_118867",
            password = "a46785b83fc98b81ae80778189c0687a",
            mobile = phone
        )
        val BASE_URL = "http://45.90.123.6:3000/"
        val apiService: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
        val requestCall = apiService.subscribe(subscribeRequestParameters)

        requestCall.enqueue(object : Callback<SubscribeResponse> {
            override fun onResponse(
                call: Call<SubscribeResponse>,
                response: Response<SubscribeResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("MyActivity", "Subscription request sent successfully: $apiResponse")
                } else {
                    // Handle unsuccessful response
                    Log.e("MyActivity", "Failed to send subscription request: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<SubscribeResponse>, t: Throwable) {
                // Handle failure
                Log.e("MyActivity", "Network error: ${t.message}")
            }
        })
    }

    fun subscriptionOff(phone: String) {
        val unsubscribeRequestParameters = UnsubscribeRequestParameters(
            appId = "APP_118867",
            password = "a46785b83fc98b81ae80778189c0687a",
            mobile = phone
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
                    Log.d("MyActivity", "Unsubscription request sent successfully: $apiResponse")
                } else {
                    // Handle unsuccessful response
                    Log.e("MyActivity", "Failed to send unsubscription request: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<UnsubscribeResponse>, t: Throwable) {
                // Handle failure
                Log.e("MyActivity", "Network error: ${t.message}")
            }
        })
    }
}