package ru.serzh272.testapplication.data.remote

import retrofit2.http.*
import ru.serzh272.testapplication.data.remote.req.UserLoginReq
import ru.serzh272.testapplication.data.remote.res.PaymentRes

interface RestService {
    @GET("payments")
    suspend fun payments():List<PaymentRes>

    @POST("login")
    suspend fun loginUser(@Body req:UserLoginReq)

}