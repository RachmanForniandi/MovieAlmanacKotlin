package com.example.moviealmanackotlin.models

data class PopularResponse(
        val total_pages:Int?,
        val results:List<MoviePopularModel>
)
