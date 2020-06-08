package com.semashko.dialogs.data.services

import com.semashko.dialogs.data.entities.Dialog

interface IDialogsService {

    fun getDialogs(): List<Dialog>?
}