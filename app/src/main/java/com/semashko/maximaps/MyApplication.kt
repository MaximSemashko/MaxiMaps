package com.semashko.maximaps

import android.app.Application
import com.semashko.maximaps.koin.initKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}