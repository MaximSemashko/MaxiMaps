package com.semashko.bookmarks.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bookmarks(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("image")
    val imageUrl: String? = null
) : Parcelable