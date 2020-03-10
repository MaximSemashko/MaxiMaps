package com.semashko.homepage.data.services

import com.semashko.homepage.data.api.HomeDataApi
import com.semashko.homepage.data.entities.TouristsRoutes
import org.koin.core.KoinComponent
import org.koin.core.inject

class HomeService : IHomeService, KoinComponent {

    private val api: HomeDataApi by inject()

    override fun getTouristsRoutes() = api.getTouristsRoutes()
}