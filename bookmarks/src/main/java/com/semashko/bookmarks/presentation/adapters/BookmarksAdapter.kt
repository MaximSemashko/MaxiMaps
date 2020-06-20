package com.semashko.bookmarks.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.bookmarks.R
import com.semashko.bookmarks.data.entities.Bookmarks
import com.semashko.provider.models.detailsPage.ItemDetails
import com.semashko.provider.navigation.INavigation
import kotlinx.android.synthetic.main.bookmark_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class BookmarksAdapter(
    private val activity: FragmentActivity?,
    private val containerViewId: Int?
) : RecyclerView.Adapter<BookmarkViewHolder>(), KoinComponent {

    private val navigation: INavigation by inject()

    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)

    private val bookmarks = mutableListOf<Bookmarks>()

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

        activity?.let {
            Glide.with(it)
                .load(bookmarks[position].imagesUrls?.get(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemView.bookmarkImageView)
        }

        holder.itemView.setOnClickListener {
            navigation.openItemDetailsPageFragment(
                containerViewId,
                activity,
                bookmarks[position].toItemDetails()
            )
        }
    }

    fun setItems(items: List<Bookmarks>) {
        bookmarks.clear()
        bookmarks.addAll(items)
        notifyDataSetChanged()
    }
}

class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(bookmark: Bookmarks) {
        itemView.bookmarkNameView.text = bookmark.name
        itemView.bookmarkTypeView.text =
            bookmark.type ?: itemView.context.getString(R.string.bookmarks)
        itemView.bookmarkExtraView.text = bookmark.description
    }
}

fun Bookmarks.toItemDetails() = ItemDetails(
    name = name,
    type = type,
    description = description,
    points = points,
    address = address,
    workingHours = workingHours,
    duration = duration,
    reviews = reviews,
    imagesUrls = imagesUrls
)

