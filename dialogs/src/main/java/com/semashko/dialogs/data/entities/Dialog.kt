package com.semashko.dialogs.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

//"-KJ_gglPD2RXoyRQpCPa" : {
//    "author" : {
//        "id" : "L9uAdEnVUmUervsmBCN1iNnNO2j2",
//        "name" : "Carl-Gustaf Harroch",
//        "photoUrl" : "https://lh6.googleusercontent.com/-PxDRhzqnR40/AAAAAAAAAAI/AAAAAAAACpQ/h_4OozlarA8/s96-c/photo.jpg"
//    },
//    "body" : "cool",
//    "timestamp" : 1465216323526
//}

@Parcelize
data class Dialog(
    @SerializedName("author")
    val author: Author? = null,

    val body: String? = null,
    val timestamp: Long? = null
) : Parcelable