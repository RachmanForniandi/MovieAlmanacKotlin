package com.example.moviealmanackotlin.models

import com.google.gson.annotations.SerializedName

class TrailerModel(
        @SerializedName("key")val key: String?,
        @SerializedName("name")val name:String?
)
