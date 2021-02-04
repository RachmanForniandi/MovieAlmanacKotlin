package com.example.moviealmanackotlin.networkUtils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL =""
class NetworkConfig {

    val endPointService: EndPointService
    get() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(EndPointService::class.java)
    }
}