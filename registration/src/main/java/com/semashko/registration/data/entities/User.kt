package com.semashko.registration.data.entities

data class User(
    private val name: String,
    private val email: String,
    private val password: String,
    private val gender: String
)