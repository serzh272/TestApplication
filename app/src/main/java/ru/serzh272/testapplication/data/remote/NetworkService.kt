package ru.serzh272.testapplication.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.serzh272.testapplication.AppConfig

object NetworkService {
    val api: RestService by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AppConfig.BASE_URL)
            .build()
        retrofit.create(RestService::class.java)
    }
}