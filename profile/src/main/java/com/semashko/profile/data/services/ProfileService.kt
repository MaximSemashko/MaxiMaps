package com.semashko.profile.data.services

import com.semashko.profile.data.api.ProfileDataApi
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProfileService : IProfileService, KoinComponent {

    private val api: ProfileDataApi by inject()

    override fun getUserProfile() = api.getUserProfile()
}