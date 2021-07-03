package ru.serzh272.testapplication.data

import com.squareup.moshi.Json
import java.math.BigDecimal
import java.util.*

data class Payment(
    @Json(name = "description")
    val description:String,
    @Json(name = "amount")
    val amount:BigDecimal = BigDecimal.ZERO,
    @Json(name = "currency")
    val currency:String,
    @Json(name = "created")
    val created:Long
){

}
