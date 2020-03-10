package com.semashko.homepage.data.entities

import android.icu.util.TimeUnit
import com.google.gson.annotations.SerializedName

data class TouristsRoutes(
    @SerializedName("name")
    val name: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("distance")
    val distance: Double,

    @SerializedName("duration")
    val duration: Long,

    @SerializedName("routes")
    val routes: List<String>,

    @SerializedName("imageUrl")
    val imageUrl: String
)