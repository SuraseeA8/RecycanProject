package com.example.lab10mysql_registerlogin.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RecycanClient {

    private const val BASE_URL = "http://10.0.2.2:3000/"

    val recycanAPI: RecycanAPI by lazy {
        retrofit.create(RecycanAPI::class.java)
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}