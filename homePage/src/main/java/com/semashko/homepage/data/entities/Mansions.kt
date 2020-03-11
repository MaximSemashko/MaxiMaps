package com.semashko.homepage.data.entities

import android.location.Address
import com.google.gson.annotations.SerializedName

data class Mansions(
    @SerializedName("name")
    val name: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("contacts")
    val contacts: List<String>?,

    @SerializedName("webSite")
    val webSite: String?,

    @SerializedName("images")
    val imagesUrls: List<String>?
)