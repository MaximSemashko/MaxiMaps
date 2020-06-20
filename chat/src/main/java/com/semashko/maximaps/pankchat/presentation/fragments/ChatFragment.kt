package com.semashko.maximaps.pankchat.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.extensions.constants.EMPTY
import com.semashko.maximaps.R
import com.semashko.maximaps.pankchat.common.EditTextListener
import com.semashko.maximaps.pankchat.data.entities.Message
import com.semashko.maximaps.pankchat.presentation.ItemUiState
import com.semashko.maximaps.pankchat.presentation.MessagesUiState
import com.semashko.maximaps.pankchat.presentation.MessagesViewModel
import com.semashko.maximaps.pankchat.presentation.adapter.AdapterMessage
import com.semashko.provider.preferences.IUserInfoPreferences
import kotlinx.android.synthetic.main.fragment_chat_room.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val USER_MODEL = "USER_MODEL"

class ChatFragment : Fragment(R.layout.fragment_chat_room), KoinComponent {

    private val viewModel: MessagesViewModel by lifecycleScope.viewModel(this)
    private val userInfoPreferences: IUserInfoPreferences by inject()

    private lateinit var adapterMessage: AdapterMessage

    private var messages: MutableList<Message> = ArrayList()

    private var user: com.semashko.provider.models.User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            user = it.getParcelable(USER_MODEL)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainToolbar.title = user?.name ?: getString(R.string.chat)
        messageText.addTextChangedListener(EditTextListener(sendButton))

        initRecyclerView()
        initSendButton()
        initSwipeToRefreshLayout()

        viewModel.messagesData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is MessagesUiState.Loading -> swipeRefreshLayout.isRefreshing = true
                is MessagesUiState.Success -> {
                    swipeRefreshLayout.isRefreshing = false
                    setItems(it.messages.filter { message -> message.toLocalId == user?.localId })
                }
                is MessagesUiState.Error -> swipeRefreshLayout.isRefreshing = false
            }
        })

        viewModel.itemData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ItemUiState.Loading -> swipeRefreshLayout.isRefreshing = true
                is ItemUiState.Success -> {
                    swipeRefreshLayout.isRefreshing = false
                }
                is ItemUiState.Error -> swipeRefreshLayout.isRefreshing = false
            }
        })

        viewModel.loadMessages()
    }

    private fun initSwipeToRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadMessages()
        }

        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }


    private fun initRecyclerView() {
        adapterMessage = AdapterMessage(requireContext(), messages)
        with(chat) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context).apply { stackFromEnd = true }
            adapter = adapterMessage
        }
    }

    private fun initSendButton() {
        sendButton.setOnClickListener {
            val message = Message(
                localId = user?.localId,
                userName = user?.name,
                message = messageText?.text.toString(),
                timestamp = System.currentTimeMillis()
            )

            userInfoPreferences.localId?.let { localId ->
                viewModel.sendMessage(
                    message.copy(
                        toLocalId = user?.localId,
                        userName = userInfoPreferences.name
                    ),
                    localId = localId
                )
            }

            Handler().postDelayed({
                user?.localId?.let { localId ->
                    viewModel.sendMessage(
                        message.copy(
                            toLocalId = userInfoPreferences.localId,
                            userName = user?.name
                        ),
                        localId = localId
                    )
                }
            }, 250)

            sendMessage(message.copy(userName = userInfoPreferences.name))

            messageText?.setText(EMPTY)

            Handler().postDelayed({
                viewModel.loadMessages()
            }, 3_000)

        }
    }

    private fun sendMessage(message: Message) {
        messages.add(message)
        adapterMessage.notifyItemInserted(messages.lastIndex)
    }

    private fun setItems(items: List<Message>) {
        messages.clear()
        messages.addAll(items)
        adapterMessage.notifyDataSetChanged()
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