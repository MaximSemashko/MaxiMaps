package com.semashko.seealldetailspage.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.provider.BaseItemDecoration
import com.semashko.provider.models.home.HomeModel
import com.semashko.seealldetailspage.R
import com.semashko.seealldetailspage.presentation.AttractionsUiState
import com.semashko.seealldetailspage.presentation.MansionsUiState
import com.semashko.seealldetailspage.presentation.RoutesUiState
import com.semashko.seealldetailspage.presentation.adapters.SeeAllAdapter
import com.semashko.seealldetailspage.presentation.viewmodels.SeeAllViewModel
import kotlinx.android.synthetic.main.fragment_see_all.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

private const val HOME_MODEL = "HOME_MODEL"

class SeeAllFragment : Fragment(R.layout.fragment_see_all) {
    private val viewModel: SeeAllViewModel by lifecycleScope.viewModel(this)

    private lateinit var seeAllAdapter: SeeAllAdapter

    private var homeModel: HomeModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            homeModel = it.getParcelable(HOME_MODEL)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initRecyclerView()
        initMoreOptionsButton()

        swipeRefreshLayout.setOnRefreshListener {
            when {
                homeModel?.attractions?.isNotEmpty() == true -> viewModel.loadAttractions()
                homeModel?.routes?.isNotEmpty() == true -> viewModel.loadRoutes()
                homeModel?.mansions?.isNotEmpty() == true -> viewModel.loadMansions()
            }
        }

        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        registerViewModelObservers()
    }

    private fun registerViewModelObservers() {
        viewModel.mansionsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is MansionsUiState.Loading -> swipeRefreshLayout.isRefreshing = true
                is MansionsUiState.Success -> {
                    seeAllAdapter.updatePage(mansions = it.mansions)
                    swipeRefreshLayout.isRefreshing = false
                }
                is MansionsUiState.Error -> swipeRefreshLayout.isRefreshing = false
            }
        })

        viewModel.routesData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RoutesUiState.Loading -> swipeRefreshLayout.isRefreshing = true
                is RoutesUiState.Success -> {
                    seeAllAdapter.updatePage(routes = it.routes)
                    swipeRefreshLayout.isRefreshing = false
                }
                is RoutesUiState.Error -> swipeRefreshLayout.isRefreshing = false
            }
        })

        viewModel.attractionsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is AttractionsUiState.Loading -> swipeRefreshLayout.isRefreshing = true
                is AttractionsUiState.Success -> {
                    seeAllAdapter.updatePage(attractions = it.attractions)
                    swipeRefreshLayout.isRefreshing = false
                }
                is AttractionsUiState.Error -> swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun initToolbar() {
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }

        when {
            homeModel?.attractions?.isNotEmpty() == true -> toolbar?.title = getString(R.string.attractions)
            homeModel?.routes?.isNotEmpty() == true -> toolbar?.title = getString(R.string.tourists_routes)
            homeModel?.mansions?.isNotEmpty() == true -> toolbar?.title = getString(R.string.mansions)
        }
    }

    private fun initMoreOptionsButton() {
//        moreOptionsButton.setOnClickListener {
//            //TODO
//        }
    }

    private fun initRecyclerView() {
        seeAllAdapter =
            SeeAllAdapter(
                activity,
                requireContext(),
                attractions = homeModel?.attractions,
                mansions = homeModel?.mansions,
                routes = homeModel?.routes
            )

        seeAllRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = seeAllAdapter
            addItemDecoration(
                BaseItemDecoration(
                    verticalMargin = resources.getDimension(R.dimen.default_padding).toInt(),
                    horizontalMargin = resources.getDimension(R.dimen.default_padding).toInt()
                )
            )
        }
    }

    companion object {
        fun newInstance(homeModel: HomeModel) =
            SeeAllFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(HOME_MODEL, homeModel)
                }
            }
    }
}
