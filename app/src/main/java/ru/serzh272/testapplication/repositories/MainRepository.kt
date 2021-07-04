package ru.serzh272.testapplication.repositories


import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import ru.serzh272.testapplication.data.Payment
import ru.serzh272.testapplication.data.managers.AppSettings
import ru.serzh272.testapplication.data.managers.PreferencesManager
import ru.serzh272.testapplication.data.remote.NetworkService
import ru.serzh272.testapplication.data.remote.req.UserLoginRequest

object MainRepository {

    private val preferencesManager = PreferencesManager()
    private val errHandler = CoroutineExceptionHandler { _, exception ->
        println("$exception")
        exception.printStackTrace()
    }
    private val api = NetworkService.api

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + errHandler)

    /**
     * gets token for authorization
     * and returns token in lambda [result]
     */
    fun getToken(userLoginReq: UserLoginRequest, result:(String?)-> Unit){
        var token: String?
        scope.launch {
            val response = api.loginUser(userLoginReq)
            token = response.getToken()
            result(token)
        }
    }
    /**
     * returns settings from datasource
     */
    fun getAppSettings():LiveData<AppSettings> = preferencesManager.settings

    /**
     * sets appSettings to datasource
     */
    fun setAppSettings(appSettings: AppSettings){
        preferencesManager.token = appSettings.token
    }
    /**
     * gets list of payments from web asynchronously
     * and returns response in lambda [result]
     */
    fun getPayments(token:String, result: (List<Payment>) -> Unit){
        scope.launch {
            val response = api.payments(token)
            val res = response.response
            result(res)
        }
    }

}