package com.semashko.homepage.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.homepage.data.entities.Attractions
import com.semashko.homepage.data.entities.Mansions
import com.semashko.homepage.data.entities.TouristsRoutes

interface IHomeRepository {

    suspend fun getTouristsRoutes(): Result<List<TouristsRoutes>>

    suspend fun getMansions(): Result<List<Mansions>>

    suspend fun getAttractions(): Result<List<Attractions>>
}