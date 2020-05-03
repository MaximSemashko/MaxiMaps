package com.semashko.seealldetailspage.data.services

import com.semashko.seealldetailspage.data.api.SeeAllDataApi
import com.semashko.seealldetailspage.data.services.ISeeAllService
import org.koin.core.KoinComponent
import org.koin.core.inject

class SeeAllService : ISeeAllService, KoinComponent {

    private val api: SeeAllDataApi by inject()

    override fun getTouristsRoutes() = api.getTouristsRoutes()

    override fun getMansion() = api.getMansions()

    override fun getAttractions() = api.getAttractions()
}