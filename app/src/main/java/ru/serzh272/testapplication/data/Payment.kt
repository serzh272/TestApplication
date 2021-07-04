package ru.serzh272.testapplication.data

import com.squareup.moshi.Json
import ru.serzh272.testapplication.extensions.format
import ru.serzh272.testapplication.extensions.toDate
import java.math.BigDecimal

data class Payment(
    @Json(name = "desc")
    val desc:String = "description",
    @Json(name = "amount")
    val amount:BigDecimal = BigDecimal.ZERO,
    @Json(name = "currency")
    val currency:String = "USD",
    @Json(name = "created")
    val created:Long = 0
){
    /**
     * returns date in specified format from [created] property
     */
    fun getDateCreated():String{
        return created.toDate().format("hh:mm dd.MM.yyyy")
    }

}
