package ru.serzh272.testapplication.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.util.TypedValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

fun Context.isNetworkAvailable():Boolean{
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetworkInfo
    return network?.isAvailable ?: false
}

fun Context.dpToPx(dp: Int): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        this.resources.displayMetrics

    )
}

fun Context.dpToIntPx(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        this.resources.displayMetrics
    ).toInt()
}

fun Context.pxToDp(px: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        px,
        this.resources.displayMetrics
    ).toInt()
}