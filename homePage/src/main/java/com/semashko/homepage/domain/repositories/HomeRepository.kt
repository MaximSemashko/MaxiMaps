package com.semashko.homepage.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.homepage.data.entities.Mansions
import com.semashko.homepage.data.entities.TouristsRoutes
import com.semashko.homepage.data.services.IHomeService
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
}