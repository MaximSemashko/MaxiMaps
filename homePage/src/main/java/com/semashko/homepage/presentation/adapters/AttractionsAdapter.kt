package com.semashko.homepage.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.homepage.R
import com.semashko.homepage.data.entities.Attractions
import kotlinx.android.synthetic.main.attractions_item.view.*
import kotlinx.android.synthetic.main.mansion_item.view.*

class AttractionsAdapter(
    private val context: Context,
    private val attractions: List<Attractions>
) : RecyclerView.Adapter<AttractionsAdapter.AttractionsViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionsViewHolder {
        return AttractionsViewHolder(
            layoutInflater.inflate(
                R.layout.attractions_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return attractions.size
    }

    override fun onBindViewHolder(holder: AttractionsViewHolder, position: Int) {
        holder.bind(attractions[position])

        Glide.with(context)
            .load(attractions[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.itemView.mansionsImageView)
    }

    class AttractionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(attractions: Attractions) {
            itemView.attractionsNameView.text = attractions.name
            itemView.attractionsTypeView.text = attractions.type
            itemView.attractionsDescriptionView.text = attractions.description
        }
    }
}
