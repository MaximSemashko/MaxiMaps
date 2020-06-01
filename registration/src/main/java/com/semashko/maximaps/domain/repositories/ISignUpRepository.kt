package com.semashko.maximaps.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.maximaps.data.entities.SignUpResponse
import com.semashko.maximaps.data.entities.User

interface ISignUpRepository {

    suspend fun signUp(user: User): Result<SignUpResponse>

    suspend fun addUserToRealtimeDatabase(user: User, localId: String?): Result<User>?
}