package com.semashko.dialogs.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.dialogs.R
import com.semashko.dialogs.data.entities.Dialog
import kotlinx.android.synthetic.main.message_item.view.*

class DialogAdapter(
    private val context: Context,
    private val dialogs: List<Dialog>
) : RecyclerView.Adapter<BookmarkViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            layoutInflater.inflate(
                R.layout.message_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dialogs.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(dialogs[position])

        Glide.with(context)
            .load(dialogs[position].author?.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.itemView.authorImageView)
    }
}

class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(dialog: Dialog) {
        itemView.authorNameView.text = dialog.author?.name
        itemView.messageView.text = dialog.body
        itemView.time.text = dialog.timestamp.toString()
    }
}

