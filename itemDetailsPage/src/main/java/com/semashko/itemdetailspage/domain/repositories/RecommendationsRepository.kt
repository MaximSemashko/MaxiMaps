package com.semashko.itemdetailspage.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.itemdetailspage.data.services.IRecommendationsService
import com.semashko.provider.models.home.Attractions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecommendationsRepository(
    private val recommendationsService: IRecommendationsService
) : IRecommendationsRepository {

    override suspend fun getAttractions(): Result<List<Attractions>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(recommendationsService.getAttractions() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }
}