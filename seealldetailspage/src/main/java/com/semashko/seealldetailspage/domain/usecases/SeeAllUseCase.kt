package com.semashko.seealldetailspage.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes
import com.semashko.seealldetailspage.domain.repositories.ISeeAllRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class SeeAllUseCase(
    private val seeAllRepository: ISeeAllRepository
) : ISeeAllUseCase {

    override suspend fun getRoutes(): Result<List<TouristsRoutes>> {
        return coroutineScope {
            val routesAsync = async { seeAllRepository.getTouristsRoutes() }

            val touristsRoutes = when (val result = routesAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            Result.Success(touristsRoutes)
        }
    }

    override suspend fun getMansions(): Result<List<Mansions>> {
        return coroutineScope {
            val mansionsAsync = async { seeAllRepository.getMansions() }

            val mansions = when (val result = mansionsAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            Result.Success(mansions)
        }
    }

    override suspend fun getAttractions(): Result<List<Attractions>> {
        return coroutineScope {
            val attractionsAsync = async { seeAllRepository.getAttractions() }

            val attractions = when (val result = attractionsAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            Result.Success(attractions)
        }
    }
}