package com.example.moviealmanackotlin.models

import com.google.gson.annotations.SerializedName

data class MoviePopularModel(
        @SerializedName("id") val id:Int,
        @SerializedName("title") val title:String,
        //@SerializedName("vote_average") val vote_average:Double?,
        @SerializedName("vote_count") val vote_count:Int?,
        @SerializedName("original_language") val original_language:String?,
        @SerializedName("backdrop_path") val backdrop_path:String?,
        @SerializedName("poster_path") val poster_path:String?,
        @SerializedName("overview") val overview:String?,
        @SerializedName("first_air_date") val first_air_date:String?,
)
