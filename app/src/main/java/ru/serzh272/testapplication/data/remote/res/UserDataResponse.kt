package ru.serzh272.testapplication.data.remote.res

import com.squareup.moshi.*


data class UserDataResponse(
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "response")
    val response: LoginResponse? = null,
    @Json(name = "error")
    val error: LoginErrorResponse? = null
) {


    fun getToken(): String? {
        var result:String? = null
        if (success) {
            result =  response?.token
        }
        return result
    }
}