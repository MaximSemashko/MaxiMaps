package com.semashko.seealldetailspage.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes

interface ISeeAllRepository {

    suspend fun getTouristsRoutes(): Result<List<TouristsRoutes>>

    suspend fun getMansions(): Result<List<Mansions>>

    suspend fun getAttractions(): Result<List<Attractions>>
}