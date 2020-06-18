package com.semashko.users.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.User
import com.semashko.users.data.services.IUsersService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository(
    private val service: IUsersService
) : IUsersRepository {

    override suspend fun getUsers(): Result<List<User>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(service.getUsers() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }
}