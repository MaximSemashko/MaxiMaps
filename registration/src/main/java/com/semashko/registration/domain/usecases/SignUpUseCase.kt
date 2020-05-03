package com.semashko.registration.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.registration.data.entities.SignUpResponse
import com.semashko.registration.data.entities.User
import com.semashko.registration.domain.repositories.ISignUpRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class SignUpUseCase(
    private val signUpRepository: ISignUpRepository
): ISignUpUseCase {

    override suspend fun signUp(user: User): Result<SignUpResponse> {
         return coroutineScope {
            val signUpAsync = async { signUpRepository.signUp(user) }

             val signUpResponse = when (val signUpResult = signUpAsync.await()) {
                 is Result.Success -> {
                     async { signUpRepository.addUserToRealtimeDatabase(user, signUpResult.value.localId) }.await()
                     signUpResult.value
                 }
                 is Result.Error -> SignUpResponse()
             }

             Result.Success(signUpResponse)
         }
    }
}