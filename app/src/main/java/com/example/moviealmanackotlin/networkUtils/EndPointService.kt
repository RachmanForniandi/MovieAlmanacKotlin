package com.example.moviealmanackotlin.networkUtils

import com.example.moviealmanackotlin.models.DetailMovieResponse
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

    @GET("popular")
    fun getMoviesPopular(
        @Query("api_key")api_key:String,
        @Query("page")page:Int
    ): Call<MovieResponse>

    @GET("/{movie_id}")
    fun getMoviesDetail(
            @Query("movie_id")movie_id:String,
            @Query("api_key")api_key:String
    ): Call<DetailMovieResponse>
}