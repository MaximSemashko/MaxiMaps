package com.semashko.homepage.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.homepage.data.services.IHomeService
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(
    private val homeService: IHomeService
) : IHomeRepository {
    override suspend fun getTouristsRoutes(): Result<List<TouristsRoutes>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(homeService.getTouristsRoutes() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }

    override suspend fun getMansions(): Result<List<Mansions>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(homeService.getMansion() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }

    override suspend fun getAttractions(): Result<List<Attractions>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(homeService.getAttractions() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }
}