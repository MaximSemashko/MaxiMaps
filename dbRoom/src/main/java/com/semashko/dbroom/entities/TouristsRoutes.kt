package com.semashko.dbroom.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tourists_routes_table")
data class TouristsRoutes(
    @PrimaryKey
    val id: Long,
    val name: String? = null,
    val type: String? = null,
    val description: String? = null,
    val points: String? = null,
    val reviews: String? = null,
    val imagesUrls: String? = null,
    val distance: String? = null,
    val duration: String? = null
)