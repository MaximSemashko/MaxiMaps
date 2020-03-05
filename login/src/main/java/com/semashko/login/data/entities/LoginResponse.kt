package com.semashko.login.data.entities

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("kind")
    val kind: String? = null,

    @SerializedName("localId")
    val localId: String? = null,

    @SerializedName("displayName")
    val displayName: String? = null,

    @SerializedName("idToken")
    val token: String? = null,

    @SerializedName("registered")
    val registered: Boolean? = null
)