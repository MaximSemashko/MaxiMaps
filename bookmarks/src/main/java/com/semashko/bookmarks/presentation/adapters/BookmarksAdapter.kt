package com.semashko.bookmarks.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.bookmarks.R
import com.semashko.bookmarks.data.entities.Bookmarks
import kotlinx.android.synthetic.main.bookmark_item.view.*

class BookmarksAdapter(
    private val context: Context,
    private val bookmarks: List<Bookmarks>
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
        return bookmarks.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(bookmarks[position])

        Glide.with(context)
            .load(bookmarks[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.itemView.bookmarkImageView)
    }
}

class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(bookmark: Bookmarks) {
        itemView.bookmarkNameView.text = bookmark.name
        itemView.bookmarkTypeView.text = bookmark.type
        itemView.bookmarkExtraView.text = bookmark.description
    }
}

