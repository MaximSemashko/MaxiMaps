package com.semashko.login.data.services

import com.semashko.login.data.entities.LoginResponse
import com.semashko.login.data.entities.User

interface ILoginService {

    fun getLoginResponse(user: User) : LoginResponse?
}