package com.semashko.maximaps.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.semashko.homepage.presentation.fragments.HomeFragment
import com.semashko.maximaps.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()
    }

    private fun initViewPager() {
        val listOfFragments =
            listOf(
                HomeFragment(),
                HomeFragment(),
                HomeFragment(),
                HomeFragment(),
                HomeFragment()
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
    private val fragmentList: List<HomeFragment>,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = fragmentList.size

    override fun getItem(position: Int): Fragment {
        if (position >= 0 && position < fragmentList.size)
            return fragmentList[position]
        return HomeFragment()
    }
}