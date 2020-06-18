package com.semashko.maximaps.pankchat.data.services

import com.semashko.maximaps.pankchat.data.api.ChatApi
import com.semashko.maximaps.pankchat.data.entities.Message
import org.koin.core.KoinComponent
import org.koin.core.inject

class ChatService() : IChatService, KoinComponent {

    private val api: ChatApi by inject()

    override fun getMessages(): List<Message>? = api.getMessages()

    override fun sendMessage(message: Message, localId: String?): Boolean =
        api.sendMessage(message, localId)
}