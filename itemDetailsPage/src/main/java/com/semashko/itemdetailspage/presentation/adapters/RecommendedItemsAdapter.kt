package com.semashko.itemdetailspage.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.itemdetailspage.R
import com.semashko.provider.models.home.Attractions
import kotlinx.android.synthetic.main.recommended_item_layout.view.*

class RecommendedItemsAdapter(
    private val context: Context,
    private val attractions: List<Attractions>
) : RecyclerView.Adapter<RecommendedItemsAdapter.RecommendedItemsViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedItemsViewHolder {
        return RecommendedItemsViewHolder(
            layoutInflater.inflate(
                R.layout.recommended_item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return attractions.size
    }

    override fun onBindViewHolder(holder: RecommendedItemsViewHolder, position: Int) {
        holder.bind(attractions[position])

        Glide.with(context)
            .load(attractions[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.itemView.recommendedImageView)

    }

    inner class RecommendedItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(attractions: Attractions) {
            itemView.recommendedNameView.text = attractions.name
        }
    }
}