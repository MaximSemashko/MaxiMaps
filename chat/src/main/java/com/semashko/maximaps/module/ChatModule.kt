package com.semashko.maximaps.module

import com.semashko.maximaps.data.repository.database.MessageRepository
import com.semashko.maximaps.data.route.ChatReferences
import com.semashko.maximaps.presentation.chatroom.ChatRoomPresenter
import org.koin.dsl.module

val chatModule = module {
    single { ChatReferences() }

    factory { MessageRepository(get()) }
    factory { ChatRoomPresenter(get()) }
}