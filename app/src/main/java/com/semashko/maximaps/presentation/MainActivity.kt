package com.semashko.maximaps.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.semashko.homepage.presentation.HomeFragment
import com.semashko.maximaps.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_page -> {
                    val fragment = HomeFragment()

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.events_page -> {
                    val fragment = HomeFragment()

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.chat_page -> {
                    val fragment = HomeFragment()

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.maps_page -> {
                    val fragment = HomeFragment()

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()

                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        LoginActivity.startActivity(this)
//        RegistrationActivity.startActivity(this)

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
