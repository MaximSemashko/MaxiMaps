package com.semashko.homepage.domain.entities

import com.semashko.homepage.data.entities.Mansions
import com.semashko.homepage.data.entities.TouristsRoutes

data class HomeModel(
    val routes: List<TouristsRoutes>?,
    val mansions: List<Mansions>?
)