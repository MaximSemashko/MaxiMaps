package com.semashko.homepage.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.homepage.data.entities.TouristsRoutes
import com.semashko.homepage.domain.repositories.IHomeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class HomeUseCase(
    private val homeRepository: IHomeRepository
) : IHomeUseCase {
    override suspend fun getTouristsRoutes(): Result<List<TouristsRoutes>> {
        return coroutineScope {
            val routesAsync = async { homeRepository.getTouristsRoutes() }

            val touristsRoutes = when (val result = routesAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            Result.Success(touristsRoutes)
        }
    }
}