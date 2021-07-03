package ru.serzh272.testapplication.data.remote.res

import com.squareup.moshi.Json
import ru.serzh272.testapplication.data.Payment

data class PaymentsResponse(
    @Json(name="success")
    val success:Boolean,
    @Json(name="response")
    val response:List<Payment>,
    @Json(name = "error")
    val error: LoginErrorResponse? = null
)