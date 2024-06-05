package com.example.bdapp

data class ApiResponse(
    val statusCode: String,
    val statusDetail: String,
    var referenceNo: String,
    val version: String
)

data class RequestParameters(
    val appId: String,
    val password: String,
    var mobile: String
)

data class OtpVerifyRespone(
    val statusCode: String,
    val version: String,
    val subscriptionStatus: String,
    val statusDetail: String,
    val subscriberId: String
)

data class VerifyParameters(
    val appId: String,
    val password: String,
    var referenceNo: String?,
    var otp: String
)

data class SubscribeRequestParameters(
    val appId: String,
    val password: String,
    val mobile: String
)

data class SubscribeResponse(
    val statusCode: String,
    val statusDetail: String,
    val subscriptionStatus: String,
    val version: String
)

data class UnsubscribeRequestParameters(
    val appId: String,
    val password: String,
    val mobile: String
)

data class UnsubscribeResponse(
    val statusCode: String,
    val statusDetail: String,
    val subscriptionStatus: String,
    val version: String
)

data class StatusResponse(
    val version: String,
    val statusCode: String,
    val statusDetail: String,
    val subscriptionStatus: String
)

data class VerifyParametersStatus(
    val appId: String,
    val password: String,
    val mobile: String
)