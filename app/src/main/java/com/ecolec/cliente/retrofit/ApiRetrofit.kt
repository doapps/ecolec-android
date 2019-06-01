package com.ecolec.cliente.retrofit

import com.ecolec.cliente.model.Recolector
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiRetrofit {

    @GET("ciudadano/recolectores")
    fun getRecolector(): Call<MutableList<Recolector>>

    @POST("ciudadano/send-publication")
    fun sendRecycler(@Body body : JsonObject) : Call<JsonObject>

    @POST("ciudadano/login")
    fun signInUser(@Body body: JsonObject): Call<JsonObject>
}