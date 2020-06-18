package com.semashko.maximaps.pankchat.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.maximaps.pankchat.data.entities.Message

interface IChatUseCase {

    suspend fun getMessages(): Result<List<Message>>

    suspend fun sendMessage(message: Message, localId: String): Result<Boolean>
}