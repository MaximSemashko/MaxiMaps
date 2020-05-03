package com.semashko.itemdetailspage.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.home.Attractions

interface IRecommendationsRepository {

    suspend fun getAttractions(): Result<List<Attractions>>
}