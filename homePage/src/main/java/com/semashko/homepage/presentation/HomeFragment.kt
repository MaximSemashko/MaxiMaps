package com.semashko.homepage.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import com.semashko.homepage.R
import com.semashko.homepage.presentation.adapters.PopularTravellersAdapter
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.popular_places.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class HomeFragment : Fragment(R.layout.home_fragment) {

    private val fragmentScope =
        getKoin().getOrCreateScope(named<HomeFragment>().toString(), named<HomeFragment>())

    private val viewModel: HomeViewModel by viewModel {
        parametersOf(currentScope.id)
    }

    private lateinit var popularTravellersAdapter: PopularTravellersAdapter
    private val travellersList = ArrayList<Travellers>()

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
            travellersList.add(
                Travellers(
                    "Maxim",
                    R.drawable.forest
                )
            )
        }
    }

    private fun initRecyclerView() {
        popularTravellersAdapter =
            PopularTravellersAdapter(
                context
            )
        popularTravellersAdapter.updateItems(travellersList)


        popularTravellersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularTravellersAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        fragmentScope.close()
    }
}
