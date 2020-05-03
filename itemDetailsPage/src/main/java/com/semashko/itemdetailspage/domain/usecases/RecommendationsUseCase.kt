package com.semashko.itemdetailspage.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.itemdetailspage.domain.repositories.IRecommendationsRepository
import com.semashko.provider.models.home.Attractions
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class RecommendationsUseCase(
    private val recommendationsRepository: IRecommendationsRepository
) : IRecommendationsUseCase {

    override suspend fun getRecommendations(): Result<List<Attractions>> {
        return coroutineScope {
            val attractionsAsync = async { recommendationsRepository.getAttractions() }

            val attractions = when (val result = attractionsAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            Result.Success(attractions)
        }
    }
}