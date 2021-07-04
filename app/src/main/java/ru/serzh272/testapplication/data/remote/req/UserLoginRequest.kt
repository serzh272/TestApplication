package ru.serzh272.testapplication.data.remote.req
/**
 * Data Class for encapsulating User request for token getting
 */
data class UserLoginRequest(
    val login:String,
    val password:String
)
