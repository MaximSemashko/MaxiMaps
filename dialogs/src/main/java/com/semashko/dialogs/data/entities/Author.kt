package com.semashko.dialogs.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Author(
    val id: String? = null,
    val name: String? = null,
    val imageUrl: String? = null
): Parcelable