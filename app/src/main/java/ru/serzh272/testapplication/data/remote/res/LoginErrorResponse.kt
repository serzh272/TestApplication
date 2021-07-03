package ru.serzh272.testapplication.data.remote.res

import com.squareup.moshi.Json

data class LoginErrorResponse(
    @Json(name = "error_code")
    val error_code:String = "000",
    @Json(name = "error_msg")
    val error_msg:String = "Error"
)
