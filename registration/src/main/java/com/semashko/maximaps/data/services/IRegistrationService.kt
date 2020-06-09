package com.semashko.maximaps.data.services

import com.semashko.maximaps.data.entities.SignUpResponse
import com.semashko.maximaps.data.entities.User

interface IRegistrationService {

    fun signUpUser(user: User): SignUpResponse?

    fun addUserToRealtimeDatabase(user: User, localId: String?): User?
}