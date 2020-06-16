package com.semashko.provider.navigation

import androidx.fragment.app.FragmentActivity
import com.semashko.provider.models.detailsPage.ItemDetails

interface INavigation {

    fun openMainActivity()

    fun openRegistrationActivity()

    fun openLoginActivity()

    fun openItemDetailsPageFragment(containerViewId: Int? = null, activity: FragmentActivity?, itemDetails: ItemDetails)
}