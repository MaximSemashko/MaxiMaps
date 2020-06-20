package com.semashko.profile.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.User

interface IProfileUseCase {

    suspend fun getUserProfile(): Result<User>
}