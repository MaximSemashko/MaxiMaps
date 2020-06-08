package com.semashko.dialogs.koin

import com.semashko.dialogs.data.api.DialogsApi
import com.semashko.dialogs.data.services.DialogsService
import com.semashko.dialogs.data.services.IDialogsService
import com.semashko.dialogs.domain.repositories.DialogsRepository
import com.semashko.dialogs.domain.repositories.IDialogsRepository
import com.semashko.dialogs.domain.usecases.DialogsUseCase
import com.semashko.dialogs.domain.usecases.IDialogUseCase
import com.semashko.dialogs.presentation.DialogViewModel
import com.semashko.dialogs.presentation.fragments.DialogsFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dialogsModule = module {
    factory {
        DialogsApi()
    }

    scope(named<DialogsFragment>()) {
        scoped<IDialogsService> { DialogsService() }
        scoped<IDialogsRepository> {
            DialogsRepository(
                service = get()
            )
        }
        scoped<IDialogUseCase> {
            DialogsUseCase(
                repository = get()
            )
        }
        viewModel {
            DialogViewModel(
                dialgUseCase = get()
            )
        }
    }
}
