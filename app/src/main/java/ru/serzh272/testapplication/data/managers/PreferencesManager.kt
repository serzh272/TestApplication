package ru.serzh272.testapplication.data.managers

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import ru.serzh272.testapplication.App
import ru.serzh272.testapplication.data.delegates.PrefDelegate
import ru.serzh272.testapplication.extensions.dataStore

/**
 * Manager for init AppSettings from datastore
 */
class PreferencesManager(context: Context = App.applicationContext()) {
    val datastore = context.dataStore
    var token by PrefDelegate("")
    val settings: LiveData<AppSettings>
        get() {
            val token = datastore.data.map { it[stringPreferencesKey(this::token.name)] ?: "" }
            return token.transform { emit(AppSettings(it)) }
                .distinctUntilChanged()
                .asLiveData()
        }
    private val errHandler = CoroutineExceptionHandler { _, th ->
        Log.d("M_PrefManager", "err ${th.message}")
    }
    internal val scope = CoroutineScope(SupervisorJob() + errHandler)
}

/**
 * Class for encapsulating Settings for application
 */
data class AppSettings(
    val token: String = ""
)