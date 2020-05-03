package com.semashko.seealldetailspage.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes
import com.semashko.seealldetailspage.data.services.ISeeAllService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeeAllRepository(
    private val seeAllService: ISeeAllService
) : ISeeAllRepository {
    override suspend fun getTouristsRoutes(): Result<List<TouristsRoutes>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(seeAllService.getTouristsRoutes() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }

    override suspend fun getMansions(): Result<List<Mansions>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(seeAllService.getMansion() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }

    override suspend fun getAttractions(): Result<List<Attractions>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(seeAllService.getAttractions() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }
}