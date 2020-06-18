package com.semashko.maximaps.pankchat.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.semashko.provider.Point
import com.semashko.provider.models.detailsPage.Reviews
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    @SerializedName("localId")
    val localId: String? = null,

    @SerializedName("userName")
    val userName: String? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("timestamp")
    val timestamp: Long? = null
) : Parcelable