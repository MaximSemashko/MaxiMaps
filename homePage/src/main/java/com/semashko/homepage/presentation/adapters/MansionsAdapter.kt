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
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.navigation.INavigation
import kotlinx.android.synthetic.main.mansion_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class MansionsAdapter(
    private val activity: FragmentActivity?
) : RecyclerView.Adapter<MansionsAdapter.MansionsViewHolder>(), KoinComponent {

    private val navigation: INavigation by inject()

    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)
    private val mansions = mutableListOf<Mansions>()

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

        activity?.let {
            Glide.with(it)
                .load(mansions[position].imagesUrls?.get(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemView.mansionsImageView)
        }

        holder.itemView.setOnClickListener {
            navigation.openItemDetailsPageFragment(
                null,
                activity,
                mansions[position].toItemDetails()
            )
        }
    }

    fun setItems(items: List<Mansions>) {
        mansions.clear()
        mansions.addAll(items)
        notifyDataSetChanged()
    }

    class MansionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mansions: Mansions) {
            itemView.mansionsNameView.text = mansions.name
            itemView.mansionsAddressView.text = mansions.address
        }
    }
}
