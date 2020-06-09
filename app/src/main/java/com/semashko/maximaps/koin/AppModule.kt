package com.semashko.maximaps.koin

import com.semashko.maximaps.preferences.UserInfoPreferences
import com.semashko.provider.preferences.IUserInfoPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<IUserInfoPreferences> {
        UserInfoPreferences(context = androidContext())
    }
}