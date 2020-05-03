package com.semashko.itemdetailspage.data.services

import com.semashko.itemdetailspage.data.api.RecommendationsDataApi
import org.koin.core.KoinComponent
import org.koin.core.inject

class RecommendationsService : IRecommendationsService, KoinComponent {

    private val api: RecommendationsDataApi by inject()

    override fun getAttractions() = api.getAttractions()
}