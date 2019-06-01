package com.ecolec.cliente.retrofit.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConfigRetrofit {
    val base: Retrofit = Retrofit.Builder()
        .baseUrl("http://api.sandbox.doapps.pe/ecolec/:4000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}