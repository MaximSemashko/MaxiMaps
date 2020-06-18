package com.semashko.comments.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.comments.R
import com.semashko.comments.data.entities.Reviews
import com.semashko.comments.presentation.CommentsUiState
import com.semashko.comments.presentation.CommentsViewModel
import com.semashko.comments.presentation.adapters.CommentsAdapter
import com.semashko.provider.BaseItemDecoration
import com.semashko.provider.models.detailsPage.ItemDetails
import com.semashko.provider.navigation.INavigation
import kotlinx.android.synthetic.main.fragment_comments.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val ITEM_DETAILS_MODEL = "ITEM_DETAILS_MODEL"

class CommentsFragment : Fragment(R.layout.fragment_comments), KoinComponent {

    private val viewModel: CommentsViewModel by lifecycleScope.viewModel(this)
    private val navigation: INavigation by inject()
    private val comments = ArrayList<Reviews>()

    private var itemDetails: ItemDetails? = null

    private lateinit var commentsAdapter: CommentsAdapter

    override fun onStart() {
        super.onStart()

        arguments?.let {
            itemDetails = it.getParcelable(ITEM_DETAILS_MODEL)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.commentsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is CommentsUiState.Loading -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                is CommentsUiState.Success -> {
                    swipeRefreshLayout.isRefreshing = false
                }
                is CommentsUiState.Error -> {
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        for (i in 1..1000) {
            comments.add(
                Reviews(
                    text = "text asd asd ghasftext asd asd ghasftext asd asd ghasftext asd asd ghasftext asd asd ghasftext asd asd ghasftext asd asd ghasftext asd asd ghasftext asd asd ghasf ",
                    timestamp = 1592416733240,
                    userName = "userName",
                    stars = 3f,
                    userImageUrl = "https://picsum.photos/seed/picsum/800/800"
                )
            )
        }

        initRecyclerView(comments)
        initToolbar()
        initSwipeToRefreshLayout()
        initWriteCommentButton()
    }

    private fun initWriteCommentButton() {
        writeCommentButton.setOnClickListener {
            itemDetails?.let { item ->
                navigation.openAddCommentFragment(
                    R.id.container,
                    activity,
                    item
                )
            }
        }
    }

    private fun initSwipeToRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadComments()
        }

        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }

    private fun initToolbar() {
        toolbar.title = "Comments"
    }

    private fun initRecyclerView(bookmarks: List<Reviews>) {
        commentsAdapter = CommentsAdapter(activity)
        commentsAdapter.setItems(bookmarks)

        commentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = commentsAdapter
            addItemDecoration(
                BaseItemDecoration(
                    verticalMargin = resources.getDimension(R.dimen.small_padding).toInt()
                )
            )
        }
    }

    companion object {
        fun newInstance(itemDetails: ItemDetails?) = CommentsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ITEM_DETAILS_MODEL, itemDetails)
            }
        }
    }
}
