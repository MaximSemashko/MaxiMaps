package com.semashko.users.data.services

import com.semashko.provider.models.User

interface IUsersService {

    fun getUsers(): List<User>?
}