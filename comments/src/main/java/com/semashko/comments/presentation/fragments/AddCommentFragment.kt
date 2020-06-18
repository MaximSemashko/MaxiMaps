package com.semashko.comments.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.semashko.comments.R
import com.semashko.comments.data.entities.Reviews
import com.semashko.comments.presentation.CommentUiState
import com.semashko.comments.presentation.CommentViewModel
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import com.semashko.provider.models.detailsPage.ItemDetails
import com.semashko.provider.preferences.IUserInfoPreferences
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import kotlinx.android.synthetic.main.fragment_add_comment.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException
import kotlin.concurrent.thread

private const val ITEM_DETAILS_MODEL = "ITEM_DETAILS_MODEL"

class AddCommentFragment : Fragment(R.layout.fragment_add_comment), KoinComponent {
    private val viewModel: CommentViewModel by lifecycleScope.viewModel(this)
    private val userInfoPreferences: IUserInfoPreferences by inject()
    private var itemDetails: ItemDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            itemDetails = it.getParcelable(ITEM_DETAILS_MODEL)
        }
        Log.i("TAG666", itemDetails.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.commentData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is CommentUiState.Loading -> {
                    progressBar.visible()
                }
                is CommentUiState.Success -> {
                    progressBar.gone()
                    if (it.itemWasAdded) {
                        activity
                            ?.supportFragmentManager
                            ?.beginTransaction()
                            ?.remove(this)
                            ?.commit()
                    }
                }
                is CommentUiState.Error -> {
                    progressBar.gone()
                }
            }

        })

        initSendButton()
        initToolbar()

        thread {
            val itemDetailsList = itemDetails?.reviews as ArrayList<Reviews>
            itemDetailsList.add(initReview())
            addItemToBookmarks(
                itemDetailsList
            )
        }
    }

    @Throws(IOException::class)
    fun addItemToBookmarks(reviews: List<Reviews>): Boolean {
        val jsonString = Gson().toJson(reviews).toString()

        val body =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/TouristsRoutes/-M9ophUmsNKLBGDaU01K/reviews.json")
            .put(body)
            .build()

        val response = OkHttpClient().newCall(request).execute()

        return response.isSuccessful
    }


    private fun initSendButton() {
        sendButton.setOnClickListener {
            viewModel.loadComment(initReview())
        }
    }

    private fun initReview(): Reviews {
        return Reviews(
            text = commentText.text.toString(),
            timestamp = System.currentTimeMillis(),
            userName = userInfoPreferences.name,
            userImageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/${userInfoPreferences.localId}?alt=media"
        )
    }

    private fun initToolbar() {
        toolbar.title = "Comment"
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }
    }

    companion object {
        fun newInstance(itemDetails: ItemDetails?) = AddCommentFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ITEM_DETAILS_MODEL, itemDetails)
            }
        }
    }
}
