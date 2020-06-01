package com.semashko.maximaps.data.entities

import com.google.gson.annotations.SerializedName

data class ResponseError(
    @SerializedName("code")
    val code: Int,

    @SerializedName("errors")
    val errors: List<Error>,

    @SerializedName("message")
    val message: String
)

data class Error(
    @SerializedName("domain")
    val domain: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("reason")
    val reason: String
)