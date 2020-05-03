package com.semashko.homepage.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.homepage.R
import com.semashko.provider.models.home.Mansions
import kotlinx.android.synthetic.main.mansion_item.view.*

class MansionsAdapter(
    private val context: Context,
    private val mansions: List<Mansions>
) : RecyclerView.Adapter<MansionsAdapter.MansionsViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MansionsViewHolder {
        return MansionsViewHolder(
            layoutInflater.inflate(
                R.layout.mansion_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mansions.size
    }

    override fun onBindViewHolder(holder: MansionsViewHolder, position: Int) {
        holder.bind(mansions[position])

        Glide.with(context)
            .load(mansions[position].imagesUrls?.get(0))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.itemView.mansionsImageView)
    }

    class MansionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mansions: Mansions) {
            itemView.mansionsNameView.text = mansions.name
            itemView.mansionsAddressView.text = mansions.address
        }
    }
}
