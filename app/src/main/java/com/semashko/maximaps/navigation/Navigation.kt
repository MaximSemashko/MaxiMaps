package com.semashko.maximaps.navigation

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.semashko.homepage.R
import com.semashko.itemdetailspage.presentation.fragments.ItemDetailsPageFragment
import com.semashko.login.presentation.LoginActivity
import com.semashko.maximaps.presentation.MainActivity
import com.semashko.maximaps.presentation.RegistrationActivity
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
}