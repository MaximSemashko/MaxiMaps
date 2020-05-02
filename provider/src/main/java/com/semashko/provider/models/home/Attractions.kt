package com.semashko.provider.models.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable