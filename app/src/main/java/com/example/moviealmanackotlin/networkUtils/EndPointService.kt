package com.example.moviealmanackotlin.networkUtils

import com.example.moviealmanackotlin.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EndPointService {

    @GET("now_playing")
    fun getMoviesNowPlaying(
        @Query("api_key")api_key:String,
        @Query("page")page:Int
    ): Call<MovieResponse>
}