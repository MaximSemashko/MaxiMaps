package com.semashko.homepage.domain.repositories

import com.semashko.extensions.utils.Result
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
                Result.Success(homeService.getTouristsRoutes())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }
}