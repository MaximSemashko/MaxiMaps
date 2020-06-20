package com.semashko.bookmarks.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.bookmarks.R
import com.semashko.bookmarks.presentation.BookmarksUiState
import com.semashko.bookmarks.presentation.BookmarksViewModel
import com.semashko.bookmarks.presentation.adapters.BookmarksAdapter
import com.semashko.provider.BaseItemDecoration
import kotlinx.android.synthetic.main.fragment_bookmarks.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {

    private val viewModel: BookmarksViewModel by lifecycleScope.viewModel(this)

    private lateinit var bookmarksAdapter: BookmarksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bookmarksData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BookmarksUiState.Loading -> swipeRefreshLayout.isRefreshing = true
                is BookmarksUiState.Success -> {
                    swipeRefreshLayout.isRefreshing = false
                    bookmarksAdapter.setItems(it.bookmarks)
                }
                is BookmarksUiState.Error -> swipeRefreshLayout.isRefreshing = false
            }
        })

        viewModel.load()

        initRecyclerView()
        initToolbar()
        initSwipeToRefreshLayout()
    }

    private fun initSwipeToRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.load()
        }

        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }

    private fun initToolbar() {
        toolbar.title = getString(R.string.bookmarks)
    }

    private fun initRecyclerView() {
        bookmarksAdapter = BookmarksAdapter(activity, R.id.bookmarkContainer)

        bookmarksRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = bookmarksAdapter
            addItemDecoration(
                BaseItemDecoration(
                    verticalMargin = resources.getDimension(R.dimen.default_padding).toInt(),
                    horizontalMargin = resources.getDimension(R.dimen.small_padding).toInt()
                )
            )
        }
    }
}
