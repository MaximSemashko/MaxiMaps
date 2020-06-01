package com.semashko.maximaps.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.maximaps.data.entities.SignUpResponse
import com.semashko.maximaps.data.entities.User

interface ISignUpUseCase {

    suspend fun signUp(user: User): Result<SignUpResponse>
}