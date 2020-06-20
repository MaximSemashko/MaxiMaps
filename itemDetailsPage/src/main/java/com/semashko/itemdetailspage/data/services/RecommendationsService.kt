package com.semashko.itemdetailspage.data.services

import com.semashko.itemdetailspage.data.api.RecommendationsDataApi
import com.semashko.provider.models.detailsPage.ItemDetails
import org.koin.core.KoinComponent
import org.koin.core.inject

class RecommendationsService : IRecommendationsService, KoinComponent {

    private val api: RecommendationsDataApi by inject()

    override fun getAttractions() = api.getAttractions()

    override fun addItemToBookmarks(itemDetails: ItemDetails) = api.addItemToBookmarks(itemDetails)
}