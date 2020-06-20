package com.semashko.maximaps.pankchat.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.semashko.maximaps.R
import com.semashko.maximaps.pankchat.data.entities.Message
import com.semashko.provider.preferences.IUserInfoPreferences
import org.koin.core.KoinComponent
import org.koin.core.inject

class AdapterMessage(context: Context, private val messages: List<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), KoinComponent {

    private val userInfoPreferences: IUserInfoPreferences by inject()

    companion object {
        const val FROM = 1
        const val TO = 2
    }

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
            is HolderMesageFrom -> holder.bindChatContent(messages[position])
            is HolderMessageTo -> holder.bindChatContent(messages[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].localId != userInfoPreferences.localId) TO else FROM
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}
