package com.semashko.provider.mappers

import com.semashko.provider.models.detailsPage.ItemDetails
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes

fun Attractions.toItemDetails() = ItemDetails(
    name = name,
    type = null,
    description = description,
    points = points,
    address = address,
    workingHours = workingHours,
    duration = null,
    reviews = reviews,
    imagesUrls = imagesUrls
)

fun Mansions.toItemDetails() = ItemDetails(
    name = name,
    type = null,
    description = description,
    points = points,
    address = address,
    workingHours = workingHours,
    duration = null,
    reviews = reviews,
    imagesUrls = imagesUrls
)

fun TouristsRoutes.toItemDetails() = ItemDetails(
    name = name,
    type = type,
    description = description,
    points = points,
    address = null,
    workingHours = null,
    duration = duration,
    reviews = reviews,
    imagesUrls = imagesUrls
)