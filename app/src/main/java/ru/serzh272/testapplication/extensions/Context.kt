package ru.serzh272.testapplication.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network

fun Context.isNetworkAvailable():Boolean{
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetworkInfo
    return network?.isAvailable ?: false
}