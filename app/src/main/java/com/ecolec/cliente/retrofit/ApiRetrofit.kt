package com.ecolec.cliente.retrofit

import com.ecolec.cliente.model.Recolector
import retrofit2.Call
import retrofit2.http.GET

interface ApiRetrofit {

    @GET("ciudadano/recolectores")
    fun getRecolector(): Call<MutableList<Recolector>>
}