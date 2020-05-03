package com.semashko.seealldetailspage.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.HomeModel
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes

interface ISeeAllUseCase {

    suspend fun getRoutes(): Result<List<TouristsRoutes>>

    suspend fun getMansions(): Result<List<Mansions>>

    suspend fun getAttractions(): Result<List<Attractions>>
}