package com.semashko.comments.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.comments.data.entities.Reviews
import com.semashko.comments.R
import com.semashko.comments.presentation.CommentsUiState
import com.semashko.comments.presentation.CommentsViewModel
import com.semashko.comments.presentation.adapters.CommentsAdapter
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import com.semashko.provider.BaseItemDecoration
import kotlinx.android.synthetic.main.fragment_bookmarks.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class  CommentsFragment : Fragment(R.layout.fragment_bookmarks) {

    private val viewModel: CommentsViewModel by lifecycleScope.viewModel(this)

    private val bookmarks = ArrayList<Reviews>()

    private lateinit var bookmarksAdapter: CommentsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.commentsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is CommentsUiState.Loading -> progressBar.visible()
                is CommentsUiState.Success -> progressBar.gone()
                is CommentsUiState.Error -> progressBar.gone()
            }
        })

        viewModel.loadComments()

        for (i in 1..1000) {
            bookmarks.add(
                Reviews(
                    text = "name",
                    timestamp = "timestamp",
                    userName = "userName",
                    imageUrl = "https://picsum.photos/seed/picsum/800/800"
                )
            )
        }

        initRecyclerView(bookmarks)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.title = "Reviews"
    }

    private fun initRecyclerView(bookmarks: List<Reviews>) {
        bookmarksAdapter =
            CommentsAdapter(
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
        fun newInstance() = CommentsFragment()
    }
}
