package com.semashko.homepage.presentation.fragments

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import com.semashko.homepage.R
import com.semashko.homepage.presentation.HomeUiState
import com.semashko.homepage.presentation.adapters.AttractionsAdapter
import com.semashko.homepage.presentation.adapters.MansionsAdapter
import com.semashko.homepage.presentation.adapters.TouristsRoutesAdapter
import com.semashko.homepage.presentation.viewmodels.HomeViewModel
import com.semashko.provider.BaseItemDecoration
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.HomeModel
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes
import com.semashko.seealldetailspage.presentation.fragments.SeeAllFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.popular_places.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import kotlin.math.abs

class HomeFragment : Fragment(R.layout.home_fragment) {

    private val viewModel: HomeViewModel by lifecycleScope.viewModel(this)

    private val touristsRoutesList = ArrayList<TouristsRoutes>()
    private val attractionsList = ArrayList<Attractions>()
    private val mansionsList = ArrayList<Mansions>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initList()
        initRecyclerView()
        initSeeAllButtons()

        viewModel.homeData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is HomeUiState.Loading -> progressBar.visible()
                is HomeUiState.Success -> progressBar.gone()
                is HomeUiState.Error -> progressBar.gone()
            }
        })

        viewModel.loadModel()
    }


    private fun initToolbar() {
        appBarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            when {
                abs(verticalOffset) - appBarLayout.totalScrollRange == 0 -> view?.findViewById<View>(
                    R.id.action_search
                )?.visible()
                else -> view?.findViewById<View>(R.id.action_search)?.gone()
            }
        })

        with(toolbar) {
            inflateMenu(R.menu.toolbar_menu)

            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_search -> Toast.makeText(
                        requireContext(),
                        "Opcion 1",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                false
            }
        }
    }

    private fun initSeeAllButtons() {
        seeAllAttractionsView.text = Html.fromHtml("<u>See all</>")
        seeAllMansionsView.text = Html.fromHtml("<u>See all</>")
        seeAllTouristsRoutesView.text = Html.fromHtml("<u>See all</>")

        seeAllTouristsRoutesView.setOnClickListener {
            openSeeAllDetailsPage(HomeModel(routes = touristsRoutesList))
        }

        seeAllAttractionsView.setOnClickListener {
            openSeeAllDetailsPage(HomeModel(attractions = attractionsList))
        }

        seeAllMansionsView.setOnClickListener {
            openSeeAllDetailsPage(HomeModel(mansions = mansionsList))
        }
    }

    private fun openSeeAllDetailsPage(homeModel: HomeModel) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(SeeAllFragment.newInstance(homeModel), HomeFragment::class.simpleName)
            ?.addToBackStack(null)
            ?.commit()
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
            touristsRoutesList.subList(0, 10)
        )

        val mansionsAdapter = MansionsAdapter(
            requireContext(),
            mansionsList.subList(0, 10)
        )

        val attractionsAdapter = AttractionsAdapter(
            requireContext(),
            attractionsList.subList(0, 10)
        )

        touristsRoutesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = touristsRoutesAdapter
            addItemDecoration(
                BaseItemDecoration(
                    horizontalMargin = resources.getDimension(R.dimen.default_padding).toInt()
                )
            )
        }

        mansionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mansionsAdapter
            addItemDecoration(
                BaseItemDecoration(
                    horizontalMargin = resources.getDimension(R.dimen.default_padding).toInt()
                )
            )
        }

        attractionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = attractionsAdapter
            addItemDecoration(
                BaseItemDecoration(
                    horizontalMargin = resources.getDimension(R.dimen.default_padding).toInt()
                )
            )
        }
    }
}
