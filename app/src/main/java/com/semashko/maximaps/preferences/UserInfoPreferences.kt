package com.semashko.maximaps.preferences

import android.content.Context
import android.content.SharedPreferences
import com.semashko.extensions.constants.EMPTY
import com.semashko.provider.preferences.IUserInfoPreferences
import com.semashko.provider.preferences.delegates.PreferencesDelegate

private const val USER_INFO = "user_info"
private const val LOCAL_ID = "local_id"
private const val TOKEN = "token"

class UserInfoPreferences(context: Context) : IUserInfoPreferences {
    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
    }

    override var localId: String? by PreferencesDelegate(preferences, LOCAL_ID, EMPTY)
    override var token: String? by PreferencesDelegate(preferences, TOKEN, EMPTY)
}