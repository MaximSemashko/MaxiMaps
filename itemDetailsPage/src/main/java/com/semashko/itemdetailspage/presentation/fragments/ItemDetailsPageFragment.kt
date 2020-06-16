package com.semashko.itemdetailspage.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.extensions.action
import com.semashko.extensions.gone
import com.semashko.extensions.snack
import com.semashko.extensions.visible
import com.semashko.itemdetailspage.R
import com.semashko.itemdetailspage.presentation.ItemUiState
import com.semashko.itemdetailspage.presentation.RecommendationsUiState
import com.semashko.itemdetailspage.presentation.adapters.PhotosAdapter
import com.semashko.itemdetailspage.presentation.adapters.RecommendedItemsAdapter
import com.semashko.itemdetailspage.presentation.viewmodels.RecommendationsViewModel
import com.semashko.maps.MapsActivity
import com.semashko.provider.BaseItemDecoration
import com.semashko.provider.models.detailsPage.ItemDetails
import com.semashko.provider.models.home.Attractions
import kotlinx.android.synthetic.main.fragment_item_details_page.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

private const val ITEM_DETAILS_MODEL = "ITEM_DETAILS_MODEL"

class ItemDetailsPageFragment : Fragment(R.layout.fragment_item_details_page) {

    private val viewModel: RecommendationsViewModel by lifecycleScope.viewModel(this)

    private val attractionsList = ArrayList<Attractions>()

    private var itemDetails: ItemDetails? = null

    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var recommendedItemsAdapter: RecommendedItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            itemDetails = it.getParcelable(ITEM_DETAILS_MODEL)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initItemDetails(itemDetails)
        initRecommendedRecyclerView()
        initShowOnMapButton()
        initAddToBookmarkImageView()

        viewModel.recommendationsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RecommendationsUiState.Loading -> progressBar.visible()
                is RecommendationsUiState.Success -> progressBar.gone()
                is RecommendationsUiState.Error -> progressBar.gone()
            }
        })

        viewModel.itemData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ItemUiState.Loading -> progressBar.visible()
                is ItemUiState.Success -> {
                    progressBar.gone()
                    addBookmarkImageView.background = resources.getDrawable(R.drawable.ic_bookmark)
                    view.snack("Bookmark was added") {
                        action("Close") {}
                    }
                }
                is ItemUiState.Error -> progressBar.gone()
            }
        })

        viewModel.loadRecommendations()
    }

    private fun initAddToBookmarkImageView() {
        addBookmarkImageView.setOnClickListener {
            itemDetails?.let { item -> viewModel.addItemToBookmarks(item) }
        }
    }

    private fun initItemDetails(itemDetails: ItemDetails?) {
        itemNameView.text = itemDetails?.name
        descriptionTextView.text = itemDetails?.description
        numberOfRevievsTextView.text = itemDetails?.reviews?.size.toString()

        if (!itemDetails?.type.isNullOrEmpty() && itemDetails?.duration != null) {
            addressTextView.text = itemDetails.type
            hoursTextView.text = itemDetails.duration.toString()
            textView2.text = "Type: "
            textView3.text = "Duration: "
        } else {
            addressTextView.text = itemDetails?.address
            hoursTextView.text = itemDetails?.workingHours
            textView2.text = "Address:"
            textView3.text = "Hours: "
        }

        initPhotosRecyclerView(itemDetails?.imagesUrls)
    }

    private fun initShowOnMapButton() {
        showOnMapButton.setOnClickListener {
            MapsActivity.startActivity(requireContext(), null)
        }
    }

    private fun initToolbar() {
        toolbar.title = itemNameView.text
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }
    }

    private fun initRecommendedRecyclerView() {
        for (i in 1..1000) {
            attractionsList.add(
                Attractions(
                    name = "Name + $i",
                    imagesUrls = listOf(
                        "https://picsum.photos/seed/picsum/300/300",
                        "https://picsum.photos/seed/picsum/300/300",
                        "https://picsum.photos/seed/picsum/300/300"
                    )
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

    private fun initPhotosRecyclerView(imagesUrls: List<String>?) {
        photosAdapter =
            PhotosAdapter(
                requireContext(),
                imagesUrls ?: emptyList()
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
        fun newInstance(itemDetails: ItemDetails) =
            ItemDetailsPageFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(ITEM_DETAILS_MODEL, itemDetails)
                    }
                }
    }
}
