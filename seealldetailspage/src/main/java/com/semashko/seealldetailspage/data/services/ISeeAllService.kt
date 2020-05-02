package com.semashko.seealldetailspage.data.services

import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes

interface ISeeAllService {

    fun getTouristsRoutes(): List<TouristsRoutes>?

    fun getMansion(): List<Mansions>?

    fun getAttractions(): List<Attractions>?
}