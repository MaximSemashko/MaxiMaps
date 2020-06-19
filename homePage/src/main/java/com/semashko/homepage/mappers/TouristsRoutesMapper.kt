package com.semashko.homepage.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.semashko.provider.Point
import com.semashko.provider.models.detailsPage.Reviews
import com.semashko.provider.models.home.TouristsRoutes
import java.util.*

fun TouristsRoutes.toDbModel(gson: Gson) = com.semashko.dbroom.entities.TouristsRoutes(
    id = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE,
    name = name,
    type = type,
    description = description,
    points = gson.toJson(points),
    reviews = gson.toJson(reviews),
    imagesUrls = gson.toJson(imagesUrls),
    distance = distance,
    duration = duration
)

fun com.semashko.dbroom.entities.TouristsRoutes.toModel(gson: Gson): TouristsRoutes =
    TouristsRoutes(
        name = this.name,
        type = this.type,
        description = this.description,
        points = gson.fromJson(
            this.points,
            object : TypeToken<List<Point?>?>() {}.type
        ),
        reviews = gson.fromJson(this.reviews, object : TypeToken<List<Reviews?>?>() {}.type),
        imagesUrls = gson.fromJson(
            this.imagesUrls,
            object : TypeToken<List<String?>?>() {}.type
        ),
        distance = this.distance,
        duration = this.duration
    )