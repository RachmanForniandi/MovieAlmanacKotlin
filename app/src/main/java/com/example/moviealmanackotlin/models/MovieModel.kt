package com.example.moviealmanackotlin.models

data class MovieModel(
    val id:Int?,
    val title:String?,
    val vote_average:Int?,
    val vote_count:Int?,
    val original_language:String?,
    val backdrop_path:String?,
    val poster_path:String?,
    val overview:String?,
    val release_date:String?,
)
