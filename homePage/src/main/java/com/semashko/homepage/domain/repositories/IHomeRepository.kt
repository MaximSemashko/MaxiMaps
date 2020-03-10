package com.semashko.homepage.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.homepage.data.entities.TouristsRoutes

interface IHomeRepository {

    suspend fun getTouristsRoutes(): Result<List<TouristsRoutes>>
}