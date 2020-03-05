package com.semashko.login.domain.repositories

import android.util.Log
import com.semashko.extensions.utils.Result
import com.semashko.login.data.entities.LoginResponse
import com.semashko.login.data.entities.User
import com.semashko.login.data.services.ILoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(
    private val loginService: ILoginService
) : ILoginRepository {
    override suspend fun login(user: User): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            runCatching {
                loginService.getLoginResponse(user)?.let {
                    Result.Success(it)
                } ?: Result.Error(IllegalStateException("No data"))
            }.getOrElse {
                Log.d("TODO", it.toString())

                Result.Error(it)
            }
        }
    }
}