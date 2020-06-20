package com.semashko.maximaps.navigation

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.semashko.comments.presentation.fragments.AddCommentFragment
import com.semashko.comments.presentation.fragments.CommentsFragment
import com.semashko.homepage.R
import com.semashko.itemdetailspage.presentation.fragments.ItemDetailsPageFragment
import com.semashko.login.presentation.LoginActivity
import com.semashko.maximaps.presentation.MainActivity
import com.semashko.maximaps.presentation.RegistrationActivity
import com.semashko.maximaps.pankchat.presentation.fragments.ChatFragment
import com.semashko.profile.presentation.fragments.ProfileFragment
import com.semashko.provider.models.detailsPage.ItemDetails
import com.semashko.provider.navigation.INavigation

class Navigation(private val context: Context) : INavigation {
    override fun openMainActivity() {
        MainActivity.startActivity(context)
    }

    override fun openRegistrationActivity() {
        RegistrationActivity.startActivity(context)
    }

    override fun openLoginActivity() {
        LoginActivity.startActivity(context)
    }

    override fun openItemDetailsPageFragment(
        containerViewId: Int?,
        activity: FragmentActivity?,
        itemDetails: ItemDetails
    ) {
        activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(
                containerViewId ?: R.id.homeContainer,
                ItemDetailsPageFragment.newInstance(itemDetails)
            )
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun openAddCommentFragment(
        containerViewId: Int?,
        activity: FragmentActivity?,
        itemDetails: ItemDetails
    ) {
        containerViewId?.let {
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(
                    it,
                    AddCommentFragment.newInstance(itemDetails)
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    override fun openCommentsFragment(containerViewId: Int?, activity: FragmentActivity?, itemDetails: ItemDetails?) {
        containerViewId?.let {
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(
                    it,
                    CommentsFragment.newInstance(itemDetails)
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    override fun openUserProfile(containerViewId: Int?, activity: FragmentActivity?, user: Any?) {
        containerViewId?.let {
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(
                    it,
                    ProfileFragment.newInstance(user as com.semashko.provider.models.User)
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    override fun openChatFragment(containerViewId: Int?, activity: FragmentActivity?, user: Any?) {
        containerViewId?.let {
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(
                    it,
                    ChatFragment.newInstance(user as com.semashko.provider.models.User)
                )
                ?.addToBackStack(null)
                ?.commit()
        }

    }
}