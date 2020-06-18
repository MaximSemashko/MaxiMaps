package com.semashko.maximaps.pankchat.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.maximaps.pankchat.data.entities.Message
import com.semashko.maximaps.pankchat.data.services.IChatService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRepository(
    private val service: IChatService
) : IChatRepository {

    override suspend fun getMessages(): Result<List<Message>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(service.getMessages() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }

    override suspend fun sendMessage(message: Message, localId: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                Result.Success(service.sendMessage(message, localId))
            }.getOrElse {
                Result.Error(it)
            }
        }
    }
}