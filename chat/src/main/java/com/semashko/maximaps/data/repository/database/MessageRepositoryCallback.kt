package com.semashko.maximaps.data.repository.database

import com.semashko.maximaps.data.entity.Chat

interface MessageRepositoryCallback {
    fun onMessageComing(chat: Chat)
    fun onMessageUpdate(position: Int, chat: Chat)
    fun onMessageDeleted(position: Int)
}