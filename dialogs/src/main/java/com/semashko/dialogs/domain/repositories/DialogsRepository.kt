package com.semashko.dialogs.domain.repositories

import com.semashko.dialogs.data.entities.Dialog
import com.semashko.dialogs.data.services.IDialogsService
import com.semashko.extensions.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DialogsRepository(
    private val service: IDialogsService
) : IDialogsRepository {

    override suspend fun getDialogs(): Result<List<Dialog>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(service.getDialogs() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }
}