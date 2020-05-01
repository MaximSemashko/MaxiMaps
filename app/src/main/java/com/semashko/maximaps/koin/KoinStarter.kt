package com.semashko.maximaps.koin

import android.app.Application
import com.semashko.homepage.koin.homeModule
import com.semashko.login.koin.loginModule
import com.semashko.registration.koin.registrationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.KoinContextHandler.getOrNull
import org.koin.core.context.startKoin

val koinModules = listOf(
    loginModule,
    registrationModule,
    homeModule
)

fun Application.initKoin() {
    if (getOrNull() == null) {
        startKoin {
            androidContext(this@initKoin)
            modules(koinModules)
        }
    }
}