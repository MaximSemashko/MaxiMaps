package com.semashko.maximaps.pankchat.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.semashko.maximaps.pankchat.data.entities.Message
import com.semashko.provider.preferences.IUserInfoPreferences
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.from_message.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class HolderMesageFrom(override val containerView: View?) :
    RecyclerView.ViewHolder(containerView as View), LayoutContainer, KoinComponent {

    private val userInfoPreferences: IUserInfoPreferences by inject()

    private val fromUsernameGroup = containerView?.fromUsernameGroup
    private val fromUsername = containerView?.fromUsername
    private val fromMessage = containerView?.fromMessage

    fun bindChatContent(message: Message) {
        if (message.localId == userInfoPreferences.localId) {
            fromUsernameGroup?.visibility = View.GONE
        } else {
            fromUsernameGroup?.visibility = View.VISIBLE
        }

        fromUsername?.text = message.userName
        fromMessage?.text = message.message
    }
}