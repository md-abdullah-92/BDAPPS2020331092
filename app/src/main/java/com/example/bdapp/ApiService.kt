package com.example.bdapp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API service interface for making network requests.
 */
interface ApiService {

    /**
     * GET request to /getResults to retrieve a list of Getdata objects.
     *
     * @return Call object for the asynchronous response containing a list of Getdata.
     */
    @GET("StudentInfo")
    // Retrieves student information based on registration number and date of birth.
    fun getStudentInfo(@Query("reg_no") regNo: Int,@Query("dateofbirth") dateofbirth:String,@Query("eiin") eiin:String): Call<List<StudentInfo>>
    @GET("StudentInfoschool")
    // Retrieves student information based on registration number and date of birth.
    fun getStudentInfoschool(@Query("reg_no") regNo: Int,@Query("dateofbirth") dateofbirth:String,@Query("cls") cls:String,@Query("eiin") eiin:String): Call<List<StudentInfoschool>>
    @GET("getResults/{semester}")
    // Retrieves results for a specific semester based on registration number.
    fun getResults(
        @Path("semester") semester: String,
        @Query("reg_no") regNo: Int,
        @Query("eiin") eiin:String
    ): Call<List<Getdata>>
    @GET("StudentFullResultsschool")
    fun getStudentFullResultsschool(
        @Query("reg_no") regNo: Int,
        @Query("cls") cls:String,
        @Query("eiin") eiin:String
    ): Call<List<Getdataschool>>

    @GET("EIINInfo")
    fun geteiininfo(
        @Query("eiin") eiin:String
    ): Call<List<Eiininfo>>


    @GET("StudentFullResults")
    // Retrieves full results for a student based on registration number.
    fun getStudentFullResults(@Query("reg_no") regNo: Int,@Query("eiin") eiin:String): Call<List<Getdata>>


    @POST("nazmul/subscription/otp/request")
    fun requestOtp(@Body requestParameters: RequestParameters): Call<ApiResponse>

    @POST("nazmul/subscription/otp/verify")
    fun verifyOtp(@Body verifyParameters: VerifyParameters): Call<OtpVerifyRespone>

    @POST("nazmul/subscription/status")
    fun verifySubscription(@Body verifyParametersStatus: VerifyParametersStatus): Call<StatusResponse>

    @POST("nazmul/subscription/subscribe")
    fun subscribe(@Body subscribeRequestParameters: SubscribeRequestParameters): Call<SubscribeResponse>

    @POST("nazmul/subscription/unsubscribe")
    fun unsubscribe(@Body unsubscribeRequestParameters: UnsubscribeRequestParameters): Call<UnsubscribeResponse>

    @POST("create")
    fun createid(@Body create: create): Call<create>

    @GET("CreateInfo")
    fun createinfo(@Query("eiin") eiin: String) : Call<List<create>>
}

