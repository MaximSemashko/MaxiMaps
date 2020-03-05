package com.semashko.login.data.services

import com.semashko.login.data.api.FirebaseAuth
import com.semashko.login.data.entities.User
import org.koin.core.KoinComponent
import org.koin.core.inject

class LoginService : ILoginService, KoinComponent {

    private val firebaseAuth: FirebaseAuth by inject()

    override fun getLoginResponse(user: User) = firebaseAuth.userLogin(user)
}