package com.semashko.provider.models.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.semashko.provider.Point
import com.semashko.provider.models.detailsPage.Reviews
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attractions(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("points")
    val points: List<Point>? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("workingHours")
    val workingHours: String? = null,

    @SerializedName("reviews")
    val reviews: List<Reviews>? = null,

    @SerializedName("imageUrl")
    val imagesUrls: List<String>? = null
) : Parcelable