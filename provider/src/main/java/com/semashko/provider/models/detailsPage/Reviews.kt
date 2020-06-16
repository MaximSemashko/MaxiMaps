package com.semashko.provider.models.detailsPage

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reviews(
    @SerializedName("text")
    val text: String? = null,

    @SerializedName("timestamp")
    val timestamp: String? = null,

    @SerializedName("userName")
    val userName: String? = null
) : Parcelable