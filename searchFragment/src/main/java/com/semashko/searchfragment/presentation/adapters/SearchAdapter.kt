package com.semashko.searchfragment.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.semashko.extensions.utils.BasePaginationAdapter
import com.semashko.searchfragment.R.*
import com.semashko.searchfragment.data.entitites.SearchItem
import kotlinx.android.synthetic.main.adapter_search_item.view.*

class SearchAdapter(private val context: Context) : BasePaginationAdapter<SearchItem>() {

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
                    itemView.searchTypeView.text = item.type
                    itemView.searchExtraView.text = item.type

                    setOnClickListener {
//                        context.startActivity(
//                            Intent(context, DetailsActivity::class.java).apply {
//                                putExtra(DETAILS_ID_KEY, item.id)
//                            }
//                        )
                    }
                }
            }
        }
    }
}