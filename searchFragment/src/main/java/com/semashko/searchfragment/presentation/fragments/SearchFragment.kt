package com.semashko.searchfragment.presentation.fragments

import android.os.Bundle
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import com.semashko.extensions.gone
import com.semashko.extensions.toEditable
import com.semashko.extensions.utils.EmptyTextWatcher
import com.semashko.extensions.utils.ScrollToEndListener
import com.semashko.extensions.visible
import com.semashko.provider.BaseItemDecoration
import com.semashko.searchfragment.R
import com.semashko.searchfragment.data.entitites.SearchItem
import com.semashko.searchfragment.presentation.SearchUiState
import com.semashko.searchfragment.presentation.adapters.SearchAdapter
import com.semashko.searchfragment.presentation.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

private const val SEARCH_INPUT = "SEARCH_INPUT"

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by lifecycleScope.viewModel(this)

    private lateinit var adapter: SearchAdapter
    private var searchInput: String? = null

    private val programsScrollListener: RecyclerView.OnScrollListener =
        ScrollToEndListener {
            loadMore()
            deleteScrollListener()
        }

    private val textWatcher: TextWatcher = object : EmptyTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            load()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            searchInput = it.getString(SEARCH_INPUT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelObserver()
        initRecycler()
        searchText.addTextChangedListener(textWatcher)

        if (searchInput != null) {
            searchText.text = searchInput.toEditable()
        }

        viewModel.load()
    }

    override fun onDestroyView() {
        searchText.removeTextChangedListener(textWatcher)

        super.onDestroyView()
    }

    private fun initRecycler() {
        adapter = SearchAdapter(activity, requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            BaseItemDecoration(
                resources.getDimension(R.dimen.default_padding).toInt(),
                resources.getDimension(R.dimen.small_padding).toInt()
            )
        )

        setScrollListener()
    }

    private fun setViewModelObserver() {
        viewModel.searchData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is SearchUiState.NewSearch -> {
                    deleteScrollListener()
                    noDataMessage.gone()
                    recyclerView.visible()
                    setRecyclerItems(emptyList())
                    adapter.showPagination()
                }
                is SearchUiState.Pagination -> {
                    adapter.showPagination()
                }
                is SearchUiState.Success -> {
                    adapter.hidePagination()

                    if (it.result.searchItems.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    } else {
                        noDataMessage.gone()
                        recyclerView.visible()
                    }

                    setRecyclerItems(it.result.searchItems)
                    setScrollListener()
                }
                is SearchUiState.Error -> {
                    adapter.hidePagination()

                    if (adapter.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    }

                    it.throwable.message?.let { message ->
                        Toast.makeText(
                            requireContext(),
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    setScrollListener()
                }
            }
        })
    }

    private fun setRecyclerItems(items: List<SearchItem>) {
        adapter.setItems(items)
    }

    private fun deleteScrollListener() {
        recyclerView.clearOnScrollListeners()
    }

    private fun setScrollListener() {
        recyclerView.clearOnScrollListeners()
        recyclerView.addOnScrollListener(programsScrollListener)
    }

    private fun load() = viewModel.load(searchText.text.toString())

    private fun loadMore() = viewModel.loadMore()

    companion object {
        fun newInstance(searchInput: String? = null) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(SEARCH_INPUT, searchInput)
                    enterTransition = Slide(Gravity.TOP)
                    exitTransition = Slide(Gravity.BOTTOM)
                }
            }
    }
}