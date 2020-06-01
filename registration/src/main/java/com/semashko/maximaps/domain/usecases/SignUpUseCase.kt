package com.semashko.maximaps.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.maximaps.data.entities.SignUpResponse
import com.semashko.maximaps.data.entities.User
import com.semashko.maximaps.domain.repositories.ISignUpRepository
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