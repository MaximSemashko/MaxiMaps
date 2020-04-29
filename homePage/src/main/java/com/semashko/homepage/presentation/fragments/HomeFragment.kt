package com.semashko.homepage.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import com.semashko.homepage.R
import com.semashko.homepage.data.entities.Attractions
import com.semashko.homepage.data.entities.Mansions
import com.semashko.homepage.data.entities.TouristsRoutes
import com.semashko.homepage.presentation.HomeUiState
import com.semashko.homepage.presentation.adapters.AttractionsAdapter
import com.semashko.homepage.presentation.adapters.MansionsAdapter
import com.semashko.homepage.presentation.adapters.TouristsRoutesAdapter
import com.semashko.homepage.presentation.viewmodels.HomeViewModel
import com.semashko.provider.BaseItemDecoration
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.popular_places.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class HomeFragment : Fragment(R.layout.home_fragment) {

    private val viewModel: HomeViewModel by lifecycleScope.viewModel(this)

    private val touristsRoutesList = ArrayList<TouristsRoutes>()
    private val attractionsList = ArrayList<Attractions>()
    private val mansionsList = ArrayList<Mansions>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initRecyclerView()

        viewModel.homeData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is HomeUiState.Loading -> {
                    progressBar.visible()
                }
                is HomeUiState.Success -> progressBar.gone()
                is HomeUiState.Error -> progressBar.gone()
            }
        })

        viewModel.loadModel()
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


    private fun initRecyclerView() {
        val touristsRoutesAdapter = TouristsRoutesAdapter(
            requireContext(),
            touristsRoutesList
        )

        val mansionsAdapter = MansionsAdapter(
            requireContext(),
            mansionsList
        )

        val attractionsAdapter = AttractionsAdapter(
            requireContext(),
            attractionsList
        )

        touristsRoutesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = touristsRoutesAdapter
            addItemDecoration(
                BaseItemDecoration(
                    resources.getDimension(R.dimen.default_padding).toInt()
                )
            )
        }

        mansionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mansionsAdapter
            addItemDecoration(
                BaseItemDecoration(
                    resources.getDimension(R.dimen.default_padding).toInt()
                )
            )
        }

        attractionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = attractionsAdapter
            addItemDecoration(
                BaseItemDecoration(
                    resources.getDimension(R.dimen.default_padding).toInt()
                )
            )
        }
    }
}
