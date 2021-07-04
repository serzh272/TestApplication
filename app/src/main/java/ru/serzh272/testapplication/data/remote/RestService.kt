package ru.serzh272.testapplication.data.remote

import retrofit2.http.*
import ru.serzh272.testapplication.data.remote.req.UserLoginRequest
import ru.serzh272.testapplication.data.remote.res.PaymentsResponse
import ru.serzh272.testapplication.data.remote.res.UserDataResponse

interface RestService {
    @Headers("app-key:12345", "v:1")
    @POST("login")
    suspend fun loginUser(@Body req:UserLoginRequest): UserDataResponse

    @Headers("app-key:12345", "v:1")
    @GET("payments")
    suspend fun payments(@Query("token") token:String = ""):PaymentsResponse



}