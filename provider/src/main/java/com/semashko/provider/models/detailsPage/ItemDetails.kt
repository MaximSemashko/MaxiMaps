package com.semashko.provider.models.detailsPage

import android.os.Parcelable
import com.semashko.provider.Point
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemDetails(
    val name: String? = null,
    val type: String? = null,
    val description: String? = null,
    val points: List<Point>? = null,
    val address: String? = null,
    val workingHours: String? = null,
    val duration: Long? = null,
    val reviews: List<Reviews>? = null,
    val imagesUrls: List<String>? = null
) : Parcelable