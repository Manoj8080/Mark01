package com.nukkadshops.mark01

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

object ApiClient {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://eadb54582831.ngrok-free.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}