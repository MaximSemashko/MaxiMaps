package com.semashko.bookmarks.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.bookmarks.R
import com.semashko.bookmarks.data.entities.Bookmarks
import com.semashko.bookmarks.presentation.BookmarksUiState
import com.semashko.bookmarks.presentation.BookmarksViewModel
import com.semashko.bookmarks.presentation.adapters.BookmarksAdapter
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import com.semashko.provider.BaseItemDecoration
import kotlinx.android.synthetic.main.fragment_bookmarks.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {

    private val viewModel: BookmarksViewModel by lifecycleScope.viewModel(this)

    private val bookmarks = ArrayList<Bookmarks>()

    private lateinit var bookmarksAdapter: BookmarksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bookmarksData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BookmarksUiState.Loading -> progressBar.visible()
                is BookmarksUiState.Success -> progressBar.gone()
                is BookmarksUiState.Error -> progressBar.gone()
            }
        })

        viewModel.load()

        for (i in 1..1000) {
            bookmarks.add(
                Bookmarks(
                    name = "name",
                    type = "name",
                    description = "lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum ",
                    imageUrl = "https://picsum.photos/seed/picsum/800/800"
                )
            )
        }

        initRecyclerView(bookmarks)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.title = "Bookmarks"
    }

    private fun initRecyclerView(bookmarks: List<Bookmarks>) {
        bookmarksAdapter =
            BookmarksAdapter(
                requireContext(),
                bookmarks
            )

        bookmarksRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = bookmarksAdapter
            addItemDecoration(
                BaseItemDecoration(
                    verticalMargin = resources.getDimension(R.dimen.default_padding).toInt()
                )
            )
        }
    }

    companion object {
        fun newInstance() = BookmarksFragment()
    }
}
