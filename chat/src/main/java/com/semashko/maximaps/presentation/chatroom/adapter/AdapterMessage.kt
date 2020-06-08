package com.semashko.maximaps.presentation.chatroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.semashko.maximaps.R
import com.semashko.maximaps.data.entity.Chat
import com.semashko.maximaps.preferences.ChatPreferences

class AdapterMessage(context: Context, private val listChat: List<Chat>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FROM = 1
        const val TO = 2
    }

    val user = ChatPreferences.initPreferences(context).userInfo.username

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FROM -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.from_message, parent, false)
                HolderMesageFrom(v)
            }

            TO -> {
                val v =
                    LayoutInflater.from(parent.context).inflate(R.layout.to_message, parent, false)
                HolderMessageTo(v)
            }

            else -> {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.from_message, parent, false)
                HolderMesageFrom(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HolderMesageFrom -> holder.bindChatContent(listChat[position])
            is HolderMessageTo -> holder.bindChatContent(listChat[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listChat[position].user == user) TO else FROM
    }

    override fun getItemCount(): Int {
        return listChat.size
    }
}
