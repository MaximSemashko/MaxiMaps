package com.semashko.dbroom.koin

import com.semashko.dbroom.MaximapsRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single {
        MaximapsRoomDatabase(
            androidContext()
        )
    }
}