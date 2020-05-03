package com.semashko.login.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.login.data.entities.LoginResponse
import com.semashko.login.data.entities.User

interface ILoginRepository {

    suspend fun login(user: User): Result<LoginResponse>
}