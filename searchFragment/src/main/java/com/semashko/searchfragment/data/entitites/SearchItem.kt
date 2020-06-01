package com.semashko.searchfragment.data.entitites

import com.google.gson.annotations.SerializedName

data class SearchItem(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("imageUrl")
    val imageUrl: String? = null
)