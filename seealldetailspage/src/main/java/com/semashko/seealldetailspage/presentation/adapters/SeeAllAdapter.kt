package com.semashko.seealldetailspage.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.provider.mappers.toItemDetails
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes
import com.semashko.provider.navigation.INavigation
import com.semashko.seealldetailspage.R
import kotlinx.android.synthetic.main.see_all_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val TYPE_ATTRACTIONS = 1
private const val TYPE_ROUTES = 2
private const val TYPE_MANSIONS = 3

class SeeAllAdapter(
    private val activity: FragmentActivity?,
    private val context: Context,
    attractions: List<Attractions>? = null,
    routes: List<TouristsRoutes>? = null,
    mansions: List<Mansions>? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), KoinComponent {

    private val navigation: INavigation by inject()

    private var attractionsList: List<Attractions> = attractions ?: emptyList()
    private var routesList: List<TouristsRoutes> = routes ?: emptyList()
    private var mansionsList: List<Mansions> = mansions ?: emptyList()

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ROUTES -> TouristsRoutesViewHolder(
                layoutInflater.inflate(
                    R.layout.see_all_item,
                    parent,
                    false
                )
            )

            TYPE_MANSIONS -> MansionsViewHolder(
                layoutInflater.inflate(
                    R.layout.see_all_item,
                    parent,
                    false
                )
            )

            TYPE_ATTRACTIONS -> AttractionsViewHolder(
                layoutInflater.inflate(
                    R.layout.see_all_item,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return attractionsList.size + routesList.size + mansionsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_ATTRACTIONS -> {
                (holder as AttractionsViewHolder).bind(attractionsList[position])

                Glide.with(context)
                    .load(attractionsList[position].imagesUrls?.get(0))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.itemView.itemImageView)

                (holder as AttractionsViewHolder).itemView.setOnClickListener {
                    navigation.openItemDetailsPageFragment(
                        null,
                        activity,
                        attractionsList[position].toItemDetails()
                    )
                }
            }
            TYPE_ROUTES -> {
                (holder as TouristsRoutesViewHolder).bind(routesList[position])

                Glide.with(context)
                    .load(routesList[position].imagesUrls?.get(0))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.itemView.itemImageView)

                (holder as TouristsRoutesViewHolder).itemView.setOnClickListener {
                    navigation.openItemDetailsPageFragment(
                        null,
                        activity,
                        routesList[position].toItemDetails()
                    )
                }
            }
            TYPE_MANSIONS -> {
                (holder as MansionsViewHolder).bind(mansionsList[position])

                Glide.with(context)
                    .load(mansionsList[position].imagesUrls?.get(0))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.itemView.itemImageView)

                (holder as MansionsViewHolder).itemView.setOnClickListener {
                    navigation.openItemDetailsPageFragment(
                        null,
                        activity,
                        mansionsList[position].toItemDetails()
                    )
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            attractionsList.isNotEmpty() -> TYPE_ATTRACTIONS
            routesList.isNotEmpty() -> TYPE_ROUTES
            mansionsList.isNotEmpty() -> TYPE_MANSIONS
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    fun updatePage(
        attractions: List<Attractions>? = null,
        routes: List<TouristsRoutes>? = null,
        mansions: List<Mansions>? = null
    ) {
        attractionsList = attractions ?: emptyList()
        routesList = routes ?: emptyList()
        mansionsList = mansions ?: emptyList()

        notifyDataSetChanged()
    }
}

class TouristsRoutesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(routes: TouristsRoutes) {
        itemView.itemNameView.text = routes.name
        itemView.itemTypeView.text = routes.type
        itemView.itemExtraView.text = routes.duration.toString()
    }
}

class MansionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(mansions: Mansions) {
        itemView.itemNameView.text = mansions.name
        itemView.itemTypeView.text = mansions.address
        itemView.itemExtraView.text = mansions.address
    }
}

class AttractionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(attractions: Attractions) {
        itemView.itemNameView.text = attractions.name
        itemView.itemTypeView.text = attractions.address
        itemView.itemExtraView.text = attractions.description
    }
}

