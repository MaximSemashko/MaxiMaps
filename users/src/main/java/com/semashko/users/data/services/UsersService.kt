package com.semashko.users.data.services

import com.semashko.users.data.api.UsersApi
import com.semashko.provider.models.User
import org.koin.core.KoinComponent
import org.koin.core.inject

class UsersService() : IUsersService, KoinComponent {

    private val api: UsersApi by inject()

    override fun getUsers(): List<User>? = api.getUsers()
}