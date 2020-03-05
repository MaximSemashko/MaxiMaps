package com.semashko.maximaps.koin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

fun Application.initKoin() {
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            androidContext(this@initKoin)
            modules(koinModules)
        }
    }
}