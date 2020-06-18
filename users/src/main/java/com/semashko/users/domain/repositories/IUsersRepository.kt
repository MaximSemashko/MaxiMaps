package com.semashko.users.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.User

interface IUsersRepository {

    suspend fun getUsers(): Result<List<User>>
}