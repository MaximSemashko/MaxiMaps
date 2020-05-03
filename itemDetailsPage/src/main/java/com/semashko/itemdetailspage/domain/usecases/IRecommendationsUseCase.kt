package com.semashko.itemdetailspage.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.HomeModel

interface IRecommendationsUseCase {

    suspend fun getRecommendations(): Result<List<Attractions>>
}