package com.semashko.itemdetailspage.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import com.semashko.itemdetailspage.R
import com.semashko.itemdetailspage.presentation.RecommendationsUiState
import com.semashko.itemdetailspage.presentation.adapters.PhotosAdapter
import com.semashko.itemdetailspage.presentation.adapters.RecommendedItemsAdapter
import com.semashko.itemdetailspage.presentation.viewmodels.RecommendationsViewModel
import com.semashko.provider.BaseItemDecoration
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.HomeModel
import kotlinx.android.synthetic.main.fragment_item_details_page.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

private const val HOME_MODEL = "HOME_MODEL"

class ItemDetailsPageFragment : Fragment(R.layout.fragment_item_details_page) {

    private val viewModel: RecommendationsViewModel by lifecycleScope.viewModel(this)

    private val photosList = ArrayList<String>()
    private val attractionsList = ArrayList<Attractions>()

    private var homeModel: HomeModel? = null

    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var recommendedItemsAdapter: RecommendedItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            homeModel = it.getParcelable(HOME_MODEL)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initPhotosRecyclerView()
        initRecommendedRecyclerView()

        viewModel.recommendationsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RecommendationsUiState.Loading -> progressBar.visible()
                is RecommendationsUiState.Success -> progressBar.gone()
                is RecommendationsUiState.Error -> progressBar.gone()
            }
        })

        viewModel.loadRecommendations()
    }

    private fun initToolbar() {
        toolbar.title = itemNameView.text
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {
            //TODO
            Toast.makeText(requireContext(), "back button", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecommendedRecyclerView() {
        for (i in 1..1000) {
            attractionsList.add(
                Attractions(
                    name = "Name + $i",
                    imageUrl = "https://picsum.photos/seed/picsum/300/300"
                )
            )
        }

        recommendedItemsAdapter =
            RecommendedItemsAdapter(
                requireContext(),
                attractionsList
            )

        with(recommendedRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendedItemsAdapter
            addItemDecoration(
                BaseItemDecoration(
                    horizontalMargin = resources.getDimension(R.dimen.small_padding).toInt(),
                    verticalMargin = resources.getDimension(R.dimen.small_padding).toInt()
                )
            )
        }
    }

    private fun initPhotosRecyclerView() {
        for (i in 1..1000) {
            photosList.add("https://picsum.photos/seed/picsum/300/300")
        }

        photosAdapter =
            PhotosAdapter(
                requireContext(),
                photosList
            )

        with(photosRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = photosAdapter
            addItemDecoration(
                BaseItemDecoration(
                    horizontalMargin = resources.getDimension(R.dimen.small_padding).toInt()
                )
            )
        }
    }

    companion object {
        fun newInstance(homeModel: HomeModel) =
            ItemDetailsPageFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(HOME_MODEL, homeModel)
                    }
                }
    }
}
