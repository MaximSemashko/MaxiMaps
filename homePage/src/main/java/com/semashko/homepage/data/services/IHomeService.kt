package com.semashko.homepage.data.services

import com.semashko.homepage.data.entities.Attractions
import com.semashko.homepage.data.entities.Mansions
import com.semashko.homepage.data.entities.TouristsRoutes

interface IHomeService {

    fun getTouristsRoutes(): List<TouristsRoutes>?

    fun getMansion(): List<Mansions>?

    fun getAttractions(): List<Attractions>?
}