package com.semashko.dialogs.domain.usecases

import com.semashko.dialogs.data.entities.Dialog
import com.semashko.extensions.utils.Result

interface IDialogUseCase {

    suspend fun getDialogs(): Result<List<Dialog>>
}