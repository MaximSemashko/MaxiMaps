package com.semashko.users.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.provider.navigation.INavigation
import com.semashko.users.R
import com.semashko.provider.models.User
import kotlinx.android.synthetic.main.user_item.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class UsersAdapter(
    private val activity: FragmentActivity?,
    private val containerViewId: Int?
) : RecyclerView.Adapter<BookmarkViewHolder>(), KoinComponent {

    private val navigation: INavigation by inject()

    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)

    private val users = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            layoutInflater.inflate(
                R.layout.user_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(users[position])

        activity?.let {
            Glide.with(it)
                .load(users[position].imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemView.userImageView)
        }

        holder.itemView.setOnClickListener {
            navigation.openUserProfile(
                containerViewId,
                activity,
                users[position]
            )
        }
    }

    fun setItems(items: List<User>) {
        users.clear()
        users.addAll(items)
        notifyDataSetChanged()
    }
}

class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(user: User) {
        itemView.userNameView.text = user.name
        itemView.userEmailView.text = user.email
    }
}


