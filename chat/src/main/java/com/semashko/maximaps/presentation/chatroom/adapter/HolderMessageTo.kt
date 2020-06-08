package com.semashko.maximaps.presentation.chatroom.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.semashko.maximaps.data.entity.Chat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.to_message.view.*

class HolderMessageTo(override val containerView: View?) :
    RecyclerView.ViewHolder(containerView as View), LayoutContainer {

    private val toUsernameGroup = containerView?.toUsernameGroup
    private val toUsername = containerView?.toUsername
    private val toMessage = containerView?.toMessage

    fun bindChatContent(chat: Chat) {
        if (chat.isSameUser == true) {
            toUsernameGroup?.visibility = View.GONE
        } else {
            toUsernameGroup?.visibility = View.VISIBLE
        }

        toUsername?.text = chat.user
        toMessage?.text = chat.message
    }
}