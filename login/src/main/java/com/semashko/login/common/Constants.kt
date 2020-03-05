package com.semashko.login.common

import com.semashko.network.BASE_URL
import com.semashko.network.FIREBASE_KEY
import com.semashko.network.OPERATION_VERIFY_PASSWORD

const val url = "$BASE_URL$OPERATION_VERIFY_PASSWORD?key=$FIREBASE_KEY"