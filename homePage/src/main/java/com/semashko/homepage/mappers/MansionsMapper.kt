package com.semashko.homepage.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.semashko.provider.Point
import com.semashko.provider.models.detailsPage.Reviews
import com.semashko.provider.models.home.Mansions
import java.util.*

fun Mansions.toDbModel(gson: Gson) = com.semashko.dbroom.entities.Mansions(
    id = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE,
    name = name,
    description = description,
    points = gson.toJson(points),
    address = address,
    workingHours = workingHours,
    reviews = gson.toJson(reviews),
    imagesUrls = gson.toJson(imagesUrls)
)

fun com.semashko.dbroom.entities.Mansions.toModel(gson: Gson): Mansions =
    Mansions(
        name = this.name,
        description = this.description,
        points = gson.fromJson(
            this.points,
            object : TypeToken<List<Point?>?>() {}.type
        ),
        address = address,
        workingHours = workingHours,
        reviews = gson.fromJson(this.reviews, object : TypeToken<List<Reviews?>?>() {}.type),
        imagesUrls = gson.fromJson(
            this.imagesUrls,
            object : TypeToken<List<String?>?>() {}.type
        )
    )