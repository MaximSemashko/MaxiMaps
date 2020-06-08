package com.semashko.dialogs.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.semashko.dialogs.R
import com.semashko.dialogs.data.entities.Author
import com.semashko.dialogs.data.entities.Dialog
import com.semashko.dialogs.presentation.DialogUiState
import com.semashko.dialogs.presentation.DialogViewModel
import com.semashko.dialogs.presentation.adapters.DialogAdapter
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import kotlinx.android.synthetic.main.fragment_dialog.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class DialogsFragment : Fragment(R.layout.fragment_dialog) {

    private val viewModel: DialogViewModel by lifecycleScope.viewModel(this)

    private val dialogs = ArrayList<Dialog>()

    private lateinit var dialogAdapter: DialogAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dialogData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DialogUiState.Loading -> progressBar.visible()
                is DialogUiState.Success -> progressBar.gone()
                is DialogUiState.Error -> progressBar.gone()
            }
        })

        viewModel.load()

        for (i in 1..1000) {
            dialogs.add(
                Dialog(
                    author = Author(
                        name = "name",
                        id = "id",
                        imageUrl = "https://picsum.photos/seed/picsum/800/800"
                    ),
                    body = "message",
                    timestamp = System.currentTimeMillis() / 1000
                )
            )
        }

        initRecyclerView(dialogs)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.title = "Dialogs"
    }

    private fun initRecyclerView(dialogs: List<Dialog>) {
        dialogAdapter =
            DialogAdapter(
                requireContext(),
                dialogs
            )

        bookmarksRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = dialogAdapter
        }
    }

    companion object {
        fun newInstance() = DialogsFragment()
    }
}
