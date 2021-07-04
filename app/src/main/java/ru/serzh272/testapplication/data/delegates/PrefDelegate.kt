package ru.serzh272.testapplication.data.delegates

import androidx.datastore.preferences.core.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.serzh272.testapplication.data.managers.PreferencesManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
/**
 * Delegate for write and read settings in datastore
 */
class PrefDelegate<T>(private val defaultValue: T, private val customKey: String? = null) {
    operator fun provideDelegate(
        thisRef: PreferencesManager,
        prop: KProperty<*>
    ): ReadWriteProperty<PreferencesManager, T> {
        val key = createKey(customKey ?: prop.name, defaultValue)
        return object :ReadWriteProperty<PreferencesManager, T>{
            private var _storedValue: T? = null
            override fun setValue(thisRef: PreferencesManager, property: KProperty<*>, value: T) {
                _storedValue = value
                thisRef.scope.launch {
                    thisRef.datastore.edit { prefs ->
                        prefs[key] = value
                    }
                }

            }

            override fun getValue(thisRef: PreferencesManager, property: KProperty<*>): T {
                if (_storedValue == null){
                    val flowValue = thisRef.datastore.data
                        .map { prefs ->
                            prefs[key] ?: defaultValue
                        }
                    _storedValue = runBlocking(Dispatchers.IO) { flowValue.first() }
                }
                return _storedValue!!
            }

        }
    }

    @Suppress("UNCHECKED_CAST")
    fun createKey(name: String, value: T): Preferences.Key<T> =
        when(value){
            is Int -> intPreferencesKey(name)
            is Long -> longPreferencesKey(name)
            is Double-> doublePreferencesKey(name)
            is Float -> floatPreferencesKey(name)
            is String -> stringPreferencesKey(name)
            is Boolean -> booleanPreferencesKey(name)
            else -> error("This type can not be stored into Preferences")
        }.run { this as Preferences.Key<T> }

}