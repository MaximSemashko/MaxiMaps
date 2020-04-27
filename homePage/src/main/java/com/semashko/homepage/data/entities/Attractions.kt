package com.semashko.homepage.data.entities

import com.google.gson.annotations.SerializedName

data class Attractions(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("Type")
    val type: String? = null,

    @SerializedName("latitude")
    val latitude: Double? = null,

    @SerializedName("longtitude")
    val longtitude: Double? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = null
)