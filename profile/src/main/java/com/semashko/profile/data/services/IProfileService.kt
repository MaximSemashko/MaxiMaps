package com.semashko.profile.data.services

import com.semashko.profile.data.entities.User
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes

interface IProfileService {

    fun getUserProfile(): User?
}