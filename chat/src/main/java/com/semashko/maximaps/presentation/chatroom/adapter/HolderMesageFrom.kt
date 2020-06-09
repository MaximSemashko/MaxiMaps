package com.semashko.maximaps.presentation.chatroom.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.semashko.maximaps.data.entity.Chat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.from_message.view.*

class HolderMesageFrom(override val containerView: View?) :
    RecyclerView.ViewHolder(containerView as View), LayoutContainer {

    private val fromUsernameGroup = containerView?.fromUsernameGroup
    private val fromUsername = containerView?.fromUsername
    private val fromMessage = containerView?.fromMessage

    fun bindChatContent(chat: Chat) {
        if (chat.isSameUser == true) {
            fromUsernameGroup?.visibility = View.GONE
        } else {
            fromUsernameGroup?.visibility = View.VISIBLE
        }

        fromUsername?.text = chat.user
        fromMessage?.text = chat.message
    }
}