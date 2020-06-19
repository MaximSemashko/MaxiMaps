package com.semashko.provider.models.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.semashko.provider.Point
import com.semashko.provider.models.detailsPage.Reviews
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TouristsRoutes(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("Type")
    val type: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("points")
    val points: List<Point>? = null,

    @SerializedName("reviews")
    val reviews: List<Reviews>? = null,

    @SerializedName("imageUrl")
    val imagesUrls: List<String>? = null,

    @SerializedName("distance")
    val distance: String? = null,

    @SerializedName("duration")
    val duration: String? = null
) : Parcelable