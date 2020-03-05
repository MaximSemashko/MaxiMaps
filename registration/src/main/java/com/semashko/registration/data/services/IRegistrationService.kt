package com.semashko.registration.data.services

import com.semashko.registration.data.entities.SignUpResponse
import com.semashko.registration.data.entities.User

interface IRegistrationService {

    fun signUpUser(user: User): SignUpResponse?

    fun addUserToRealtimeDatabase(user: User, localId: String?): User?
}