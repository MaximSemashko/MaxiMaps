package com.semashko.maximaps.pankchat.koin

import com.semashko.maximaps.pankchat.presentation.MessagesViewModel
import com.semashko.maximaps.pankchat.data.api.ChatApi
import com.semashko.maximaps.pankchat.data.services.ChatService
import com.semashko.maximaps.pankchat.data.services.IChatService
import com.semashko.maximaps.pankchat.domain.repositories.ChatRepository
import com.semashko.maximaps.pankchat.domain.repositories.IChatRepository
import com.semashko.maximaps.pankchat.domain.usecases.ChatUseCase
import com.semashko.maximaps.pankchat.domain.usecases.IChatUseCase
import com.semashko.maximaps.pankchat.presentation.fragments.ChatFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val messageModule = module {
    factory {
        ChatApi()
    }

    scope(named<ChatFragment>()) {
        scoped<IChatService> { ChatService() }
        scoped<IChatRepository> {
            ChatRepository(
                service = get()
            )
        }
        scoped<IChatUseCase> {
            ChatUseCase(
                repository = get()
            )
        }
        viewModel {
            MessagesViewModel(
                chatUseCase = get()
            )
        }
    }
}
