package com.semashko.homepage.data.entities

import android.location.Address
import com.google.gson.annotations.SerializedName

data class Mansions(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("contacts")
    val contacts: List<String>? = null,

    @SerializedName("webSite")
    val webSite: String? = null,

    @SerializedName("images")
    val imagesUrls: List<String>? = null
)