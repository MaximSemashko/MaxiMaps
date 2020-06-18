package com.semashko.maximaps.presentation.chatroom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.maximaps.R
import com.semashko.maximaps.common.Constant
import com.semashko.maximaps.common.EditTextListener
import com.semashko.maximaps.data.entity.Chat
import com.semashko.maximaps.preferences.ChatPreferences
import com.semashko.maximaps.presentation.chatroom.adapter.AdapterMessage
import kotlinx.android.synthetic.main.fragment_chat_room.*
import org.koin.android.ext.android.inject

private const val USER_MODEL = "USER_MODEL"

class ChatFragment : Fragment(R.layout.fragment_chat_room), ChatRoomView {

    private lateinit var adapterMessage: AdapterMessage
    private val chatRoomPresenter by inject<ChatRoomPresenter>()
    private var listChat: MutableList<Chat> = ArrayList()

    private var user: com.semashko.provider.models.User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            user = it.getParcelable(USER_MODEL)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = ChatPreferences.initPreferences(requireContext()).userInfo
        mainToolbar.title = "Chat"

        etMessage.addTextChangedListener(EditTextListener(btnSend))

        adapterMessage = AdapterMessage(requireContext(), listChat)

        with(chat) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
            adapter = adapterMessage
        }

        btnSend.setOnClickListener {
            val username = user.username
            val message = etMessage?.text.toString()
            val time = Constant.time

            val chat = Chat()
            chat.user = username
            chat.message = message
            chat.time = time

            chatRoomPresenter.sendMessage(chat)

            etMessage?.setText("")
        }

        chatRoomPresenter.attachView(this)
        chatRoomPresenter.getMessages()
    }

    override fun onMessageComing(chat: Chat) {
        listChat.add(chat)
        adapterMessage.notifyItemInserted(listChat.lastIndex)
    }

    override fun onMessageUpdate(position: Int, chat: Chat) {
        listChat[position] = chat
        adapterMessage.notifyItemChanged(position, chat)
    }

    override fun onMessageDeleted(position: Int) {
        listChat.removeAt(position)
        adapterMessage.notifyItemRemoved(position)
    }

    override fun onDestroy() {
        super.onDestroy()
        chatRoomPresenter.detachView()
    }

    companion object {
        fun newInstance(user: com.semashko.provider.models.User?) = ChatFragment()
            .apply {
                arguments = Bundle().apply {
                    putParcelable(USER_MODEL, user)
                }
            }
    }
}