package com.semashko.login.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.login.data.entities.LoginResponse
import com.semashko.login.data.entities.User
import com.semashko.login.domain.repositories.ILoginRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class LoginUseCase(
    private val loginRepository: ILoginRepository
) : ILoginUseCase {

    override suspend fun login(user: User): Result<LoginResponse> {
        return coroutineScope {
            val loginAsync = async { loginRepository.login(user) }

            val loginResponse = when (val loginResult = loginAsync.await()) {
                is Result.Success -> loginResult.value
                is Result.Error -> LoginResponse()
            }

            Result.Success(loginResponse)
        }
    }
}