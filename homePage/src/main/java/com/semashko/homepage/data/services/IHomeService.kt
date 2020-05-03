package com.semashko.homepage.data.services

import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes

interface IHomeService {

    fun getTouristsRoutes(): List<TouristsRoutes>?

    fun getMansion(): List<Mansions>?

    fun getAttractions(): List<Attractions>?
}