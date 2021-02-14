package com.example.moviealmanackotlin.networkUtils

import com.example.moviealmanackotlin.models.DetailMovieResponse
import com.example.moviealmanackotlin.models.MovieResponse
import com.example.moviealmanackotlin.models.PopularResponse
import com.example.moviealmanackotlin.models.TrailerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EndPointService {

    @GET("movie/now_playing")
    fun getMoviesNowPlaying(
        @Query("api_key")api_key:String,
        @Query("page")page:Int
    ): Call<MovieResponse>

    @GET("movie/popular")
    fun getMoviesPopular(
        @Query("api_key")api_key:String,
        @Query("page")page:Int
    ): Call<PopularResponse>

    @GET("movie/{movie_id}")
    fun getMoviesDetail(
            @Path("movie_id")movie_id:Int,
            @Query("api_key")api_key:String
    ): Call<DetailMovieResponse>

    @GET("movie/{movie_id}/videos")
    fun getMoviesTrailer(
            @Path("movie_id")movie_id:Int,
            @Query("api_key")api_key:String
    ): Call<TrailerResponse>
}