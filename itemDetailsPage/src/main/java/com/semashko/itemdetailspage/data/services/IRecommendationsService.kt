package com.semashko.itemdetailspage.data.services

import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes

interface IRecommendationsService {

    fun getAttractions(): List<Attractions>?
}