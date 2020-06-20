package com.semashko.itemdetailspage.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.itemdetailspage.R
import kotlinx.android.synthetic.main.item_layout.view.*

class PhotosAdapter(
    private val context: Context,
    private val photosList: List<String>
) : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(layoutInflater.inflate(R.layout.item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        Glide.with(context)
            .load(photosList[position])
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.itemView.itemImageView)

    }

    inner class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}