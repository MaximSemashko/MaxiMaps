package com.semashko.provider

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Point(
    val latitude: Double? = null,
    val longitude: Double? = null
) : Parcelable