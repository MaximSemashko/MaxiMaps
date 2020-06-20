package com.semashko.itemdetailspage.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.itemdetailspage.R
import com.semashko.provider.mappers.toItemDetails
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.navigation.INavigation
import kotlinx.android.synthetic.main.recommended_item_layout.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class RecommendedItemsAdapter(
    private val activity: FragmentActivity?
) : RecyclerView.Adapter<RecommendedItemsAdapter.RecommendedItemsViewHolder>(), KoinComponent {

    private val navigation: INavigation by inject()

    private val layoutInflater = LayoutInflater.from(activity)

    private val attractions = mutableListOf<Attractions>()

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

    fun setItems(items: List<Attractions>) {
        attractions.clear()
        attractions.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecommendedItemsViewHolder, position: Int) {
        holder.bind(attractions[position])

        activity?.let {
            Glide.with(it)
                .load(attractions[position].imagesUrls?.get(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemView.recommendedImageView)
        }

        holder.itemView.setOnClickListener {
            navigation.openItemDetailsPageFragment(
                null,
                activity,
                attractions[position].toItemDetails()
            )
        }
    }

    inner class RecommendedItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(attractions: Attractions) {
            itemView.recommendedNameView.text = attractions.name
        }
    }
}