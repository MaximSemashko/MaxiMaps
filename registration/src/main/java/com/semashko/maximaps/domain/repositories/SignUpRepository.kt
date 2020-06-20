package com.semashko.maximaps.domain.repositories

import android.util.Log
import com.semashko.extensions.utils.Result
import com.semashko.maximaps.data.entities.SignUpResponse
import com.semashko.maximaps.data.entities.User
import com.semashko.maximaps.data.services.IRegistrationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException

class SignUpRepository(
    private val registrationService: IRegistrationService
) : ISignUpRepository {
    override suspend fun signUp(user: User): Result<SignUpResponse> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                registrationService.signUpUser(user)?.let {
                    Result.Success(it)
                } ?: Result.Error(IllegalStateException("TODO"))
            }.getOrElse {
                Log.d("TODO", it.toString())

                Result.Error(it)
            }
        }
    }

    override suspend fun addUserToRealtimeDatabase(user: User, localId: String?): Result<User>? {
        return withContext(Dispatchers.IO) {
            runCatching {
                registrationService.addUserToRealtimeDatabase(user, localId)?.let {
                    Result.Success(it)
                } ?: Result.Error(IllegalStateException("TODO"))
            }.getOrElse {
                Log.d("TODO", it.toString())

                Result.Error(it)
            }
        }
    }
}