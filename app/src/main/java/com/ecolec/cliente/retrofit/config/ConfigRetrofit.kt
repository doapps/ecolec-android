package com.ecolec.cliente.retrofit.config

import com.ecolec.cliente.retrofit.ApiRetrofit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ConfigRetrofit {

    private const val TIMEOUT = 30L

    private lateinit var retrofit: Retrofit
    private val okHttpClient = OkHttpClient
        .Builder()
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

    fun instance(): ApiRetrofit {
        retrofit = Retrofit.Builder()
            .baseUrl("http://api.sandbox.doapps.pe/ecolec/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiRetrofit::class.java)
    }
}