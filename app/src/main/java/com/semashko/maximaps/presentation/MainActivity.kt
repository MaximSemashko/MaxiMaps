package com.semashko.maximaps.presentation

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.semashko.bookmarks.presentation.fragments.BookmarksFragment
import com.semashko.homepage.presentation.fragments.HomeFragment
import com.semashko.login.presentation.LoginActivity
import com.semashko.maximaps.R
import com.semashko.profile.presentation.fragments.ProfileFragment
import com.semashko.provider.preferences.IUserInfoPreferences
import com.semashko.users.presentation.fragments.UsersFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val userInfoPreferences: IUserInfoPreferences by inject()

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            bottomNavigationViewLinear.setCurrentActiveItem(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (userInfoPreferences.localId.isNullOrEmpty() || userInfoPreferences.token.isNullOrEmpty()) {
            LoginActivity.startActivity(this)
        }

        initViewPager()
    }

    private fun initViewPager() {
        val listOfFragments =
            listOf(
                HomeFragment(),
                BookmarksFragment(),
                UsersFragment.newInstance(),
                ProfileFragment.newInstance(null)
            )

        val screenSlidePagerAdapter = ScreenSlidePagerAdapter(
            this,
            listOfFragments
        )

        viewPager.apply {
            adapter = screenSlidePagerAdapter
            isUserInputEnabled = false
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

    companion object {
        fun startActivity(context: Context) {
            with(context) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
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