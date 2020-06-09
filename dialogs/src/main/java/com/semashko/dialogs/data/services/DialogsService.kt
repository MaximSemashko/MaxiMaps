package com.semashko.dialogs.data.services

import com.semashko.dialogs.data.api.DialogsApi
import com.semashko.dialogs.data.entities.Dialog
import org.koin.core.KoinComponent
import org.koin.core.inject

class DialogsService : IDialogsService, KoinComponent {

    private val dialogsApi: DialogsApi by inject()

    override fun getDialogs(): List<Dialog>? = dialogsApi.getDialogs()
}