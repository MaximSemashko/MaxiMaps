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
import com.semashko.provider.Point
import com.semashko.provider.models.detailsPage.ItemDetails
import com.semashko.provider.navigation.INavigation
import kotlinx.android.synthetic.main.fragment_item_details_page.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val ITEM_DETAILS_MODEL = "ITEM_DETAILS_MODEL"

class ItemDetailsPageFragment : Fragment(R.layout.fragment_item_details_page), KoinComponent {

    private val viewModel: RecommendationsViewModel by lifecycleScope.viewModel(this)
    private val navigation: INavigation by inject()

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
        initRecommendationsRecyclerView()
        initShowOnMapButton()
        initAddToBookmarkImageView()
        initComments()

        viewModel.recommendationsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RecommendationsUiState.Loading -> progressBar.visible()
                is RecommendationsUiState.Success -> {
                    recommendedItemsAdapter.setItems(it.attractions)
                    progressBar.gone()
                }
                is RecommendationsUiState.Error -> progressBar.gone()
            }
        })

        viewModel.itemData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ItemUiState.Loading -> progressBar.visible()
                is ItemUiState.Success -> {
                    progressBar.gone()
                    addBookmarkImageView.background = resources.getDrawable(R.drawable.ic_bookmark)
                    view.snack(getString(R.string.bookmark_was_added)) {
                        action(getString(R.string.close)) {}
                    }
                }
                is ItemUiState.Error -> progressBar.gone()
            }
        })

        viewModel.loadRecommendations()
    }

    private fun initComments() {
        addCommentButton.setOnClickListener {
            navigation.openCommentsFragment(R.id.container, activity, itemDetails)
        }
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
            textView2.text = getString(R.string.type)
            textView3.text = getString(R.string.duration)
        } else {
            addressTextView.text = itemDetails?.address
            hoursTextView.text = itemDetails?.workingHours
            textView2.text = getString(R.string.address)
            textView3.text = getString(R.string.hours)
        }

        initPhotosRecyclerView(itemDetails?.imagesUrls)
    }

    private fun initShowOnMapButton() {
        showOnMapButton.setOnClickListener {
            MapsActivity.startActivity(
                requireContext(),
                itemDetails?.points as ArrayList<Point>?
            )
        }
    }

    private fun initToolbar() {
        toolbar.title =
            if (itemNameView.text.isNullOrEmpty()) getString(R.string.details) else itemNameView.text
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
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

    private fun initRecommendationsRecyclerView() {
        recommendedItemsAdapter = RecommendedItemsAdapter(activity)

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
