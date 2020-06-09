package com.semashko.dialogs.domain.repositories

import com.semashko.dialogs.data.entities.Dialog
import com.semashko.extensions.utils.Result

interface IDialogsRepository {

    suspend fun getDialogs(): Result<List<Dialog>>
}