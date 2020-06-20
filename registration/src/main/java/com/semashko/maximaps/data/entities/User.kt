package com.semashko.maximaps.data.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("birthDay")
    val birthDay: String? = null,

    @SerializedName("localId")
    val localId: String? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = null
)
