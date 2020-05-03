package com.semashko.provider.models.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeModel(
    val attractions: List<Attractions>? = null,
    val routes: List<TouristsRoutes>? = null,
    val mansions: List<Mansions>? = null
) : Parcelable