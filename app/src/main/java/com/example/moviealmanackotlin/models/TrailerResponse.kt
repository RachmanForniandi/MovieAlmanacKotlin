package com.example.moviealmanackotlin.models

import com.google.gson.annotations.SerializedName

data class TrailerResponse(
        @SerializedName("results") val results: List<TrailerModel>
)
