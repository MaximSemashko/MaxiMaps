package com.semashko.profile.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.profile.data.entities.User
import com.semashko.profile.data.services.IProfileService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRepository(
    private val profileService: IProfileService
) : IProfileRepository {

    override suspend fun getUserProfile(): Result<User> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(profileService.getUserProfile() ?: User())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }
}