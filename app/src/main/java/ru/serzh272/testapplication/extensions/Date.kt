package ru.serzh272.testapplication.extensions

import java.text.SimpleDateFormat
import java.util.*


fun Date.format(pattern:String = "HH:mm:ss dd.MM.yy"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}