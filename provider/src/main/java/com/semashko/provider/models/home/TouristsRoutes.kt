package com.semashko.provider.models.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TouristsRoutes(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("distance")
    val distance: Double? = null,

    @SerializedName("duration")
    val duration: Long? = null,

    @SerializedName("routes")
    val routes: List<String>? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = null
) : Parcelable