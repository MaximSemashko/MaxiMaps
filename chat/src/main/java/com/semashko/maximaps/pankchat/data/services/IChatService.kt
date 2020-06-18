package com.semashko.maximaps.pankchat.data.services

import com.semashko.maximaps.pankchat.data.entities.Message

interface IChatService {

    fun getMessages(): List<Message>?

    fun sendMessage(message: Message, localId: String?): Boolean
}