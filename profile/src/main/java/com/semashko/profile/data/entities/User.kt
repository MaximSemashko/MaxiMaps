package com.semashko.profile.data.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("birthDay")
    val birthDay: String? = null,

    @SerializedName("profileImageUrl")
    val profileImageUrl: String? = null
)