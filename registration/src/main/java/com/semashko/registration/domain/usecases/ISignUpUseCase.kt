package com.semashko.registration.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.registration.data.entities.SignUpResponse
import com.semashko.registration.data.entities.User

interface ISignUpUseCase {

    suspend fun signUp(user: User): Result<SignUpResponse>
}