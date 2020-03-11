package com.semashko.homepage.data.entities

import com.google.gson.annotations.SerializedName

data class Attractions(
    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("Type")
    val type: String?,

    @SerializedName("latitude")
    val latitude: Double?,

    @SerializedName("longtitude")
    val longtitude: Double?,

    @SerializedName("imageUrl")
    val imageUrl: String?
)