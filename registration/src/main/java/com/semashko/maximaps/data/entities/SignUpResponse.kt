package com.semashko.maximaps.data.entities

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("kind")
    val kind: String? = null,

    @SerializedName("idToken")
    val token: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("refreshToken")
    val refreshToken: String? = null,

    @SerializedName("expiresIn")
    val expiresIn: String? = null,

    @SerializedName("localId")
    val localId: String? = null
)