package com.semashko.maximaps.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.semashko.homepage.presentation.fragments.HomeFragment
import com.semashko.itemdetailspage.presentation.fragments.ItemDetailsPageFragment
import com.semashko.login.presentation.LoginActivity
import com.semashko.maximaps.R
import com.semashko.maximaps.presentation.chatroom.ChatFragment
import com.semashko.profile.presentation.fragments.ProfileFragment
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.HomeModel
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val touristsRoutesList = ArrayList<TouristsRoutes>()
    private val attractionsList = ArrayList<Attractions>()
    private val mansionsList = ArrayList<Mansions>()

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            bottomNavigationViewLinear.setCurrentActiveItem(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoginActivity.startActivity(this)

        initList()
        initViewPager()
    }

    private fun initList() {
        for (i in 1..1000) {
            touristsRoutesList.add(
                TouristsRoutes(
                    name = "name",
                    type = "name",
                    imageUrl = "https://picsum.photos/seed/picsum/800/800"
                )
            )

            mansionsList.add(
                Mansions(
                    name = "name",
                    address = "wtf street",
                    imagesUrls = listOf(
                        "https://picsum.photos/seed/picsum/800/800",
                        "https://picsum.photos/seed/picsum/800/800",
                        "https://picsum.photos/seed/picsum/800/800"
                    )
                )
            )

            attractionsList.add(
                Attractions(
                    name = "name",
                    type = "type",
                    description = "salam popolam",
                    imageUrl = "https://picsum.photos/seed/picsum/800/800"
                )
            )
        }
    }

    private fun initViewPager() {
        val listOfFragments =
            listOf(
                ChatFragment(),
                HomeFragment(),
                ProfileFragment.newInstance(),
                ItemDetailsPageFragment.newInstance(HomeModel(mansions = mansionsList))
            )

        val screenSlidePagerAdapter = ScreenSlidePagerAdapter(
            this,
            listOfFragments
        )

        viewPager.apply {
            adapter = screenSlidePagerAdapter
            registerOnPageChangeCallback(onPageChangeCallback)

            bottomNavigationViewLinear.setNavigationChangeListener { _, position ->
                viewPager.setCurrentItem(position, true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }
}

class ScreenSlidePagerAdapter(
    activity: AppCompatActivity,
    private val fragmentList: List<Fragment>
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        if (position >= 0 && position < fragmentList.size)
            return fragmentList[position]
        return Fragment()
    }
}