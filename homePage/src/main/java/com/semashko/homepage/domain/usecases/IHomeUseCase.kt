package com.semashko.homepage.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.homepage.data.entities.TouristsRoutes

interface IHomeUseCase {

    suspend fun getTouristsRoutes(): Result<List<TouristsRoutes>>
}