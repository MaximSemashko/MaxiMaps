package com.semashko.searchfragment.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.semashko.extensions.utils.BasePaginationAdapter
import com.semashko.provider.models.detailsPage.ItemDetails
import com.semashko.provider.navigation.INavigation
import com.semashko.searchfragment.R.layout
import com.semashko.searchfragment.data.entitites.SearchItem
import kotlinx.android.synthetic.main.adapter_search_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class SearchAdapter(
    private val activity: FragmentActivity?,
    private val context: Context
) : BasePaginationAdapter<SearchItem>(), KoinComponent {

    private val navigation: INavigation by inject()

    override val itemLayout: Int = layout.adapter_search_item

    override val paginationLayout: Int = layout.adapter_pagination

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun setItems(items: Collection<SearchItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemViewHolder(parent: ViewGroup): BaseItemViewHolder =
        ItemViewHolder(inflater.inflate(itemLayout, parent, false))

    override fun getPaginationViewHolder(parent: ViewGroup): PaginationViewHolder =
        PaginationViewHolder(inflater.inflate(paginationLayout, parent, false))

    inner class ItemViewHolder(view: View) : BaseItemViewHolder(view) {

        override fun bind(item: Any) {
            if (item is SearchItem) {
                Glide.with(context)
                    .load("https://picsum.photos/seed/picsum/300/300")
                    .into(itemView.searchImageView)

                itemView.apply {
                    itemView.searchNameView.text = item.name
//                    itemView.searchTypeView.text = item.description
//                    itemView.searchExtraView.text = item.duration.toString()

                    setOnClickListener {
                        navigation.openItemDetailsPageFragment(
                            null,
                            activity,
                            item.toItemDetails()
                        )
                    }
                }
            }
        }
    }
}

fun SearchItem.toItemDetails() = ItemDetails(
    name = name,
    type = type,
    description = description,
    points = points,
    address = null,
    workingHours = null,
    duration = duration,
    reviews = reviews,
    imagesUrls = imagesUrls
)