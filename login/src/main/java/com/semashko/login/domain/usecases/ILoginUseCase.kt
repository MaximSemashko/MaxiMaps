package com.semashko.login.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.login.data.entities.LoginResponse
import com.semashko.login.data.entities.User

interface ILoginUseCase {

    suspend fun login(user: User): Result<LoginResponse>
}