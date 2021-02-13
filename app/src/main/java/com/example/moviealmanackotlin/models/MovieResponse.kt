package com.example.moviealmanackotlin.models

data class MovieResponse (
    val total_pages:Int?,
    val results:List<MovieModel>
    )