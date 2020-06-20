package com.semashko.itemdetailspage.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.detailsPage.ItemDetails
import com.semashko.provider.models.home.Attractions

interface IRecommendationsUseCase {

    suspend fun getRecommendations(): Result<List<Attractions>>

    suspend fun addItemToBookmarks(itemDetails: ItemDetails): Result<Boolean>
}