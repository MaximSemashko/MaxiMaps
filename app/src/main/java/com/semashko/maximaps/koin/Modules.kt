package com.semashko.maximaps.koin

import com.semashko.homepage.koin.HomeDependency
import com.semashko.login.koin.LoginDependency
import com.semashko.registration.koin.RegistrationDependency

val koinModules = listOf(
    LoginDependency.module,
    RegistrationDependency.module,
    HomeDependency.module
)
