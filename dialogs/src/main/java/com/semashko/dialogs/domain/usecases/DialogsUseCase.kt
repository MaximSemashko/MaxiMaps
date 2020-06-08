package com.semashko.dialogs.domain.usecases

import com.semashko.dialogs.data.entities.Dialog
import com.semashko.dialogs.domain.repositories.IDialogsRepository
import com.semashko.extensions.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class DialogsUseCase(
    private val repository: IDialogsRepository
) : IDialogUseCase {

    override suspend fun getDialogs(): Result<List<Dialog>> {
        return coroutineScope {
            val bookmarksAsync = async { repository.getDialogs() }

            val bookmarks = when (val result = bookmarksAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            Result.Success(bookmarks)
        }
    }
}