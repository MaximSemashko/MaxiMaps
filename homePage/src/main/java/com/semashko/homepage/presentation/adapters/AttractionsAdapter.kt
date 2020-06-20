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
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.navigation.INavigation
import kotlinx.android.synthetic.main.attractions_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class AttractionsAdapter(
    private val activity: FragmentActivity?
) : RecyclerView.Adapter<AttractionsAdapter.AttractionsViewHolder>(), KoinComponent {

    private val navigation: INavigation by inject()

    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)
    private val attractions = mutableListOf<Attractions>()

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

        activity?.let {
            Glide.with(it)
                .load(attractions[position].imagesUrls?.get(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemView.attractionsImageView)
        }

        holder.itemView.setOnClickListener {
            navigation.openItemDetailsPageFragment(
                null,
                activity,
                attractions[position].toItemDetails()
            )
        }
    }

    fun setItems(items: List<Attractions>) {
        attractions.clear()
        attractions.addAll(items)
        notifyDataSetChanged()
    }

    class AttractionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(attractions: Attractions) {
            itemView.attractionsNameView.text = attractions.name
            itemView.attractionsAddressView.text = attractions.address
            itemView.attractionsDescriptionView.text = attractions.description
        }
    }
}
