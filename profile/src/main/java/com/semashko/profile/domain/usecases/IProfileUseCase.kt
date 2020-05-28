package com.semashko.profile.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.profile.data.entities.User

interface IProfileUseCase {

    suspend fun getUserProfile(): Result<User>
}