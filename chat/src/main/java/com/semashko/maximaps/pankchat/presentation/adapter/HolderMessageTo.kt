package com.semashko.maximaps.pankchat.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.semashko.maximaps.pankchat.data.entities.Message
import com.semashko.provider.preferences.IUserInfoPreferences
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.to_message.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class HolderMessageTo(override val containerView: View?) :
    RecyclerView.ViewHolder(containerView as View), LayoutContainer, KoinComponent {

    private val userInfoPreferences: IUserInfoPreferences by inject()

    private val toUsernameGroup = containerView?.toUsernameGroup
    private val toUsername = containerView?.toUsername
    private val toMessage = containerView?.toMessage

    fun bindChatContent(message: Message) {
        if (message.localId == userInfoPreferences.localId) {
            toUsernameGroup?.visibility = View.GONE
        } else {
            toUsernameGroup?.visibility = View.VISIBLE
        }

        toUsername?.text = message.userName
        toMessage?.text = message.message
    }
}