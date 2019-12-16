package com.semashko.maximaps.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.maximaps.R
import com.semashko.maximaps.popularTravellers.PopularTravellersAdapter
import com.semashko.maximaps.popularTravellers.Travellers
import kotlinx.android.synthetic.main.popular_places.*

class HomeFragment : Fragment(R.layout.home_fragment) {

    private lateinit var popularTravellersAdapter: PopularTravellersAdapter
    private val travellersList = ArrayList<Travellers>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initRecyclerView()
    }

    private fun initList() {
        for (i in 1..1000) {
            travellersList.add(Travellers("Maxim",
                R.drawable.forest
            ))
        }
    }

    private fun initRecyclerView() {
        popularTravellersAdapter = PopularTravellersAdapter(context)
        popularTravellersAdapter.updateItems(travellersList)


        popularTravellersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularTravellersAdapter
        }
    }
}
