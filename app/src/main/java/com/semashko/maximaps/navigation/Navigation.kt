package com.semashko.maximaps.navigation

import android.content.Context
import com.semashko.login.presentation.LoginActivity
import com.semashko.maximaps.presentation.MainActivity
import com.semashko.maximaps.presentation.RegistrationActivity
import com.semashko.provider.navigation.INavigation

class Navigation(private val context: Context) : INavigation {
    override fun openMainActivity() {
        MainActivity.startActivity(context)
    }

    override fun openRegistrationActivity() {
        RegistrationActivity.startActivity(context)
    }

    override fun openLoginActivity() {
        LoginActivity.startActivity(context)
    }
}