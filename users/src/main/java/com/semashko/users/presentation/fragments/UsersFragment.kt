package com.semashko.users.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.provider.BaseItemDecoration
import com.semashko.provider.preferences.IUserInfoPreferences
import com.semashko.users.R
import com.semashko.users.presentation.UsersUiState
import com.semashko.users.presentation.UsersViewModel
import com.semashko.users.presentation.adapters.UsersAdapter
import kotlinx.android.synthetic.main.fragment_users.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class UsersFragment : Fragment(R.layout.fragment_users), KoinComponent {

    private val userInfoPreferences: IUserInfoPreferences by inject()

    private val viewModel: UsersViewModel by lifecycleScope.viewModel(this)

    private lateinit var usersAdapter: UsersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.usersData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is UsersUiState.Loading -> swipeRefreshLayout.isRefreshing = true
                is UsersUiState.Success -> {
                    swipeRefreshLayout.isRefreshing = false
                    it.users.forEach { user ->
                        if (user.localId == userInfoPreferences.localId) {
                            userInfoPreferences.name = user.name
                        }
                    }
                    usersAdapter.setItems(it.users.filter { user -> user.localId != userInfoPreferences.localId })
                }
                is UsersUiState.Error -> swipeRefreshLayout.isRefreshing = false
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
        toolbar.title = getString(R.string.users)
    }

    private fun initRecyclerView() {
        usersAdapter = UsersAdapter(activity, R.id.userContainer)

        usersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = usersAdapter
            addItemDecoration(
                BaseItemDecoration(
                    verticalMargin = resources.getDimension(R.dimen.small_padding).toInt()
                )
            )
        }
    }

    companion object {
        fun newInstance() = UsersFragment()
    }
}
