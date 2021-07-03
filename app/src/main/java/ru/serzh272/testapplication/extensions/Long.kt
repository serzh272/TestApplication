package ru.serzh272.testapplication.extensions

import java.util.*

fun Long.toDate():Date{
    return Date(this)
}