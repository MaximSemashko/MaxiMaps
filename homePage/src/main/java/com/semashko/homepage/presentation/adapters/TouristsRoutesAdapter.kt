package com.semashko.homepage.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.homepage.R
import com.semashko.provider.models.home.TouristsRoutes
import kotlinx.android.synthetic.main.tourists_route_item.view.*

class TouristsRoutesAdapter(
    private val context: Context,
    private val touristsRoutes: List<TouristsRoutes>
) : RecyclerView.Adapter<TouristsRoutesAdapter.TouristsRoutesViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristsRoutesViewHolder {
        return TouristsRoutesViewHolder(
            layoutInflater.inflate(
                R.layout.tourists_route_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return touristsRoutes.size
    }

    override fun onBindViewHolder(holder: TouristsRoutesViewHolder, position: Int) {
        holder.bind(touristsRoutes[position])

        Glide.with(context)
            .load(touristsRoutes[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.itemView.touristRouteImageView)
    }

    class TouristsRoutesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(touristsRoute: TouristsRoutes) {
            itemView.touristRouteNameView.text = touristsRoute.name
            itemView.touristRouteDurationView.text = touristsRoute.type
        }
    }
}
