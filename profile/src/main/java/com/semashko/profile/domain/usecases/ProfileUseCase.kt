package com.semashko.profile.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.User
import com.semashko.profile.domain.repositories.IProfileRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class ProfileUseCase(
    private val profileRepository: IProfileRepository
) : IProfileUseCase {

    override suspend fun getUserProfile(): Result<User> {
        return coroutineScope {
            val userAsync = async { profileRepository.getUserProfile() }

            val user = when (val result = userAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> User()
            }

            Result.Success(user)
        }
    }
}