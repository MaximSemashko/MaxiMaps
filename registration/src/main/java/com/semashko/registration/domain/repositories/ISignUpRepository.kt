package com.semashko.registration.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.registration.data.entities.SignUpResponse
import com.semashko.registration.data.entities.User

interface ISignUpRepository {

    suspend fun signUp(user: User): Result<SignUpResponse>

    suspend fun addUserToRealtimeDatabase(user: User, localId: String?): Result<User>?
}