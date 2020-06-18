package com.semashko.users.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.User

interface IUsersUseCase {

    suspend fun getUsers(): Result<List<User>>
}