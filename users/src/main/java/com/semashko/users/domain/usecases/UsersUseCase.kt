package com.semashko.users.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.User
import com.semashko.users.domain.repositories.IUsersRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class UsersUseCase(
    private val repository: IUsersRepository
) : IUsersUseCase {

    override suspend fun getUsers(): Result<List<User>> {
        return coroutineScope {
            val usersAsync = async { repository.getUsers() }

            val users = when (val result = usersAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            Result.Success(users)
        }
    }
}