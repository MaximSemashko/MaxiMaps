package com.semashko.maximaps.data.services

import com.semashko.maximaps.data.api.FirebaseRegistration
import com.semashko.maximaps.data.entities.User
import org.koin.core.KoinComponent
import org.koin.core.inject

class RegistrationService : IRegistrationService, KoinComponent {

    private val firebaseRegistration: FirebaseRegistration by inject()

    override fun signUpUser(user: User) = firebaseRegistration.signUpUser(user)

    override fun addUserToRealtimeDatabase(user: User, localId: String?) =
        firebaseRegistration.addUserToRealtimeDatabase(user, localId)
}