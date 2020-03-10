package com.semashko.homepage.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.semashko.homepage.R
import com.semashko.homepage.presentation.Travellers
import kotlinx.android.synthetic.main.traveller_item.view.*

class PopularTravellersAdapter(
    private val context: Context?
) : RecyclerView.Adapter<PopularTravellersAdapter.PopularTravellersViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val travellersList = ArrayList<Travellers>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PopularTravellersViewHolder(
            layoutInflater.inflate(
                R.layout.traveller_item,
                parent,
                false
            )
        )

    override fun getItemCount() = travellersList.size

    override fun onBindViewHolder(holder: PopularTravellersViewHolder, position: Int) {
        holder.bind(travellersList[position])
    }

    fun updateItems(items: List<Travellers>) {
        travellersList.clear()
        travellersList.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(items: Travellers) {
        travellersList.add(items)
        notifyDataSetChanged()
    }

    class PopularTravellersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Travellers) {
            itemView.travellerNameView.text = item.name
            itemView.travellerImageView.setImageResource(item.imageResource)
        }
    }
}