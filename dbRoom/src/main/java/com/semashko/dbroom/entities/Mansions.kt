package com.semashko.dbroom.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "mansions_table")
data class Mansions(
    @PrimaryKey
    val id: Long,
    val name: String? = null,
    val description: String? = null,
    val points: String? = null,
    val address: String? = null,
    val workingHours: String? = null,
    val reviews: String? = null,
    val imagesUrls: String? = null
)