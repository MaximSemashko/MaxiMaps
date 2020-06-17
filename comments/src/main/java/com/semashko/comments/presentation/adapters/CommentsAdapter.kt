package com.semashko.comments.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.comments.R
import com.semashko.comments.data.entities.Reviews
import kotlinx.android.synthetic.main.bookmark_item.view.*

class CommentsAdapter(
    private val context: Context,
    private val reviews: List<Reviews>
) : RecyclerView.Adapter<BookmarkViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            layoutInflater.inflate(
                R.layout.bookmark_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(reviews[position])

        Glide.with(context)
            .load(reviews[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.itemView.bookmarkImageView)
    }
}

class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(bookmark: Reviews) {
        itemView.bookmarkNameView.text = bookmark.userName
        itemView.bookmarkTypeView.text = bookmark.text
        itemView.bookmarkExtraView.text = bookmark.timestamp
    }
}

