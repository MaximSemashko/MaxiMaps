package com.semashko.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.getDateTime(): String? {
    val date = Date(this)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}