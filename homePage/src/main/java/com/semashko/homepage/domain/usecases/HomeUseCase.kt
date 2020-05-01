package com.semashko.homepage.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.homepage.domain.entities.HomeModel
import com.semashko.homepage.domain.repositories.IHomeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class HomeUseCase(
    private val homeRepository: IHomeRepository
) : IHomeUseCase {
    override suspend fun getHomeModel(): Result<HomeModel> {
        return coroutineScope {
            val attractionsAsync = async { homeRepository.getAttractions() }
            val routesAsync = async { homeRepository.getTouristsRoutes() }
            val mansionsAsync = async { homeRepository.getMansions() }

            val attractions = when (val result = attractionsAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            val touristsRoutes = when (val result = routesAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            val mansions = when (val result = mansionsAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            Result.Success(
                HomeModel(
                    attractions = attractions,
                    routes = touristsRoutes,
                    mansions = mansions
                )
            )
        }
    }
}