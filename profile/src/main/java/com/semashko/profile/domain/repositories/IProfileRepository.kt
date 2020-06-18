package com.semashko.profile.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.User

interface IProfileRepository {

    suspend fun getUserProfile(): Result<User>
}