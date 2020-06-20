package com.semashko.profile.data.services

import com.semashko.provider.models.User

interface IProfileService {

    fun getUserProfile(): User?
}