package com.semashko.maximaps.pankchat.domain.repositories

import com.semashko.maximaps.pankchat.data.entities.Message
import com.semashko.extensions.utils.Result

interface IChatRepository {

    suspend fun getMessages(): Result<List<Message>>

    suspend fun sendMessage(message: Message, localId: String) : Result<Boolean>
}