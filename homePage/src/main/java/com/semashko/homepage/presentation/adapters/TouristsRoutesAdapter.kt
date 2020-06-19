package com.semashko.homepage.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.homepage.R
import com.semashko.provider.mappers.toItemDetails
import com.semashko.provider.models.home.TouristsRoutes
import com.semashko.provider.navigation.INavigation
import kotlinx.android.synthetic.main.tourists_route_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class TouristsRoutesAdapter(
    private val activity: FragmentActivity?
) : RecyclerView.Adapter<TouristsRoutesAdapter.TouristsRoutesViewHolder>(), KoinComponent {

    private val navigation: INavigation by inject()
    private val touristsRoutes = mutableListOf<TouristsRoutes>()

    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)

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

        activity?.let {
            Glide.with(it)
                .load(touristsRoutes[position].imagesUrls?.get(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemView.touristRouteImageView)
        }

        holder.itemView.setOnClickListener {
            navigation.openItemDetailsPageFragment(
                null,
                activity,
                touristsRoutes[position].toItemDetails()
            )
        }
    }

    fun setItems(items: List<TouristsRoutes>) {
        touristsRoutes.clear()
        touristsRoutes.addAll(items)
        notifyDataSetChanged()
    }

    class TouristsRoutesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(touristsRoute: TouristsRoutes) {
            itemView.touristRouteNameView.text = touristsRoute.name
            itemView.touristRouteDurationView.text = touristsRoute.duration
        }
    }
}
