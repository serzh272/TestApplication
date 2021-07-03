package ru.serzh272.testapplication.repositories


import kotlinx.coroutines.*
import ru.serzh272.testapplication.data.Payment
import ru.serzh272.testapplication.data.remote.NetworkService
import ru.serzh272.testapplication.data.remote.req.UserLoginRequest

object MainRepository {
    private val errHandler = CoroutineExceptionHandler { _, exception ->
        println("$exception")
        exception.printStackTrace()
    }
    private val api = NetworkService.api

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + errHandler)
    fun getToken(userLoginReq: UserLoginRequest, result:(String?)-> Unit){
        var token: String?
        scope.launch {
            val response = api.loginUser(userLoginReq)
            token = response.getToken()
            result(token)
        }
    }

    fun getPayments(token:Int, result: (List<Payment>) -> Unit){
        scope.launch {
            val response = api.payments(token)
            val res = response.response
            result(res)
        }
    }

}