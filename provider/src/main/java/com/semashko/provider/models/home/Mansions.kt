package com.semashko.provider.models.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable