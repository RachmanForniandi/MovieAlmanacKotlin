package com.example.moviealmanackotlin.models

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(
        @SerializedName("id") val id: Int?,
        @SerializedName("title") val title: String?,
        @SerializedName("backdrop_path") val backdrop_path: String?,
        @SerializedName("overview") val overview: String?,
        @SerializedName("vote_average") val vote_average: Double?,
        @SerializedName("release_date") val release_date: String?,
        @SerializedName("genres") var genres: List<GenreModel>?
)
