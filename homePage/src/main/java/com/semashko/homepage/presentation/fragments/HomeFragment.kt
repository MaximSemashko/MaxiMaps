package com.semashko.homepage.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.semashko.extensions.constants.EMPTY
import com.semashko.extensions.gone
import com.semashko.extensions.toEditable
import com.semashko.extensions.utils.EmptyTextWatcher
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
import com.semashko.provider.preferences.IUserInfoPreferences
import com.semashko.searchfragment.presentation.fragments.SearchFragment
import com.semashko.seealldetailspage.presentation.fragments.SeeAllFragment
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.popular_places.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.math.abs

class HomeFragment : Fragment(R.layout.home_fragment), KoinComponent {

    private val viewModel: HomeViewModel by lifecycleScope.viewModel(this)

    private val userInfoPreferences: IUserInfoPreferences by inject()

    private lateinit var touristsRoutesAdapter: TouristsRoutesAdapter
    private lateinit var attractionsAdapter: AttractionsAdapter
    private lateinit var mansionsAdapter: MansionsAdapter

    private val touristsRoutesList = ArrayList<TouristsRoutes>()
    private val attractionsList = ArrayList<Attractions>()
    private val mansionsList = ArrayList<Mansions>()

    private val textWatcher: TextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length != null && s.length >= 3) {
                openSearchFragment(s.toString())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initRecyclerView()
        initSeeAllButtons()
        initSearchInputLayout()
        initPersonImageView()

        viewModel.homeData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is HomeUiState.Loading -> progressBar.visible()
                is HomeUiState.Success -> {
                    progressBar.gone()

                    it.homeModel.routes?.let { items -> touristsRoutesAdapter.setItems(items) }
                    it.homeModel.mansions?.let { items -> mansionsAdapter.setItems(items) }
                    it.homeModel.attractions?.let { items -> attractionsAdapter.setItems(items) }

                    initList(it.homeModel)
                }
                is HomeUiState.Error -> progressBar.gone()
            }
        })

        viewModel.loadModel()
    }

    private fun initPersonImageView() {
        Glide.with(requireContext())
            .load("https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/${userInfoPreferences.localId}?alt=media")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(personImageView)
    }

    private fun initList(homeModel: HomeModel?) {
        homeModel?.routes?.let { touristsRoutesList.addAll(it) }
        homeModel?.mansions?.let { mansionsList.addAll(it) }
        homeModel?.attractions?.let { attractionsList.addAll(it) }
    }

    private fun initSearchInputLayout() {
        searchInputLayout.editText?.addTextChangedListener(textWatcher)
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

            setOnMenuItemClickListener {
                openSearchFragment(null)

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

    private fun openSearchFragment(searchInput: String?) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.homeContainer, SearchFragment.newInstance(searchInput))
            ?.addToBackStack(null)
            ?.commit()

        searchInputLayout.editText?.text = EMPTY.toEditable()
    }

    private fun openSeeAllDetailsPage(homeModel: HomeModel) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.homeContainer, SeeAllFragment.newInstance(homeModel))
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun initRecyclerView() {
        touristsRoutesAdapter = TouristsRoutesAdapter(
            activity
        )

        mansionsAdapter = MansionsAdapter(
            activity
        )

        attractionsAdapter = AttractionsAdapter(
            activity
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
