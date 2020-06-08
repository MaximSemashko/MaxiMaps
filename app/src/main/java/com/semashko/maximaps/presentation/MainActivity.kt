package com.semashko.maximaps.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.semashko.dialogs.presentation.fragments.DialogsFragment
import com.semashko.homepage.presentation.fragments.HomeFragment
import com.semashko.itemdetailspage.presentation.fragments.ItemDetailsPageFragment
import com.semashko.maximaps.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                DialogsFragment(),
                HomeFragment(),
                ProfileFragment.newInstance(),
                ItemDetailsPageFragment.newInstance(HomeModel(mansions = mansionsList))
            )

        val screenSlidePagerAdapter = ScreenSlidePagerAdapter(
            listOfFragments,
            supportFragmentManager
        )

        viewPager.apply {
            adapter = screenSlidePagerAdapter
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    bottomNavigationViewLinear.setCurrentActiveItem(position)
                }
            })

            bottomNavigationViewLinear.setNavigationChangeListener { _, position ->
                viewPager.setCurrentItem(position, true)
            }
        }
    }
}

class ScreenSlidePagerAdapter(
    private val fragmentList: List<Fragment>,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = fragmentList.size

    override fun getItem(position: Int): Fragment {
        if (position >= 0 && position < fragmentList.size)
            return fragmentList[position]
        return Fragment()
    }
}