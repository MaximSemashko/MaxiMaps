package com.semashko.maximaps.pankchat.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.maximaps.pankchat.data.entities.Message
import com.semashko.maximaps.pankchat.domain.repositories.IChatRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class ChatUseCase(
    private val repository: IChatRepository
) : IChatUseCase {

    override suspend fun getMessages(): Result<List<Message>> {
        return coroutineScope {
            val messagesAsync = async { repository.getMessages() }

            val messages = when (val result = messagesAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            Result.Success(messages)
        }
    }

    override suspend fun sendMessage(message: Message, localId: String): Result<Boolean> {
        return coroutineScope {
            val messageAsync = async { repository.sendMessage(message, localId) }

            val messageAwait = when (val result = messageAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> false
            }

            Result.Success(messageAwait)
        }
    }
}