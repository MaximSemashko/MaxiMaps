package com.semashko.comments.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.comments.R
import com.semashko.comments.data.entities.Reviews
import com.semashko.extensions.getDateTime
import kotlinx.android.synthetic.main.comments_item.view.*

class CommentsAdapter(
    private val activity: FragmentActivity?
) : RecyclerView.Adapter<BookmarkViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)

    private val reviews = mutableListOf<Reviews>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            layoutInflater.inflate(
                R.layout.comments_item,
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

        activity?.let {
            Glide.with(it)
                .load(reviews[position].userImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemView.commentsImageView)
        }
    }

    fun setItems(items: List<Reviews>) {
        reviews.clear()
        reviews.addAll(items)
        notifyDataSetChanged()
    }
}

class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(review: Reviews) {
        itemView.userNameView.text = review.userName
        itemView.commentsTextView.text = review.text
        itemView.ratingBar.rating = review.stars ?: 0.0f
        itemView.commentsTimeView.text = review.timestamp?.getDateTime()
    }
}

