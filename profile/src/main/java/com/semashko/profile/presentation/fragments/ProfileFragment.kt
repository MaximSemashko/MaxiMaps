package com.semashko.profile.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.semashko.extensions.constants.EMPTY
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import com.semashko.profile.R
import com.semashko.profile.presentation.ProfileUiState
import com.semashko.profile.presentation.viewmodels.ProfileViewModel
import com.semashko.provider.models.User
import com.semashko.provider.navigation.INavigation
import com.semashko.provider.preferences.IUserInfoPreferences
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val USER_MODEL = "USER_MODEL"

class ProfileFragment : Fragment(R.layout.fragment_profile), KoinComponent {

    private val viewModel: ProfileViewModel by lifecycleScope.viewModel(this)
    private val navigation: INavigation by inject()

    private var userModel: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            userModel = it.getParcelable(USER_MODEL)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (userModel == null) {
            viewModel.profileData.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is ProfileUiState.Loading -> progressBar.visible()
                    is ProfileUiState.Success -> {
                        progressBar.gone()
                        initUserInfo(it.user)
                    }
                    is ProfileUiState.Error -> progressBar.gone()
                }
            })

            initExitButton()
            viewModel.loadModel()
        } else {
            initUserInfo(userModel ?: throw Throwable("Model is empty"))

            exitButton.text = getString(R.string.message)
            exitButton.setOnClickListener {
                navigation.openChatFragment(
                    R.id.profileContainer,
                    activity,
                    userModel ?: throw Throwable("Model is empty")
                )
            }
        }
    }

    private fun initExitButton() {
        exitButton.setOnClickListener {
            ExitDialogFragment.confirmExit(childFragmentManager)
        }
    }

    private fun initUserInfo(user: User) {
        Glide.with(this)
            .load(user.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(profileImageView)

        profileNameTextView.text = user.name ?: EMPTY
        profileEmailTextView.text = user.email ?: EMPTY
        profileNameView.text = user.name ?: EMPTY
        profilePhoneView.text = user.phone ?: EMPTY
        profileEmailView.text = user.email ?: EMPTY
        profileAddressView.text = user.address ?: EMPTY
        profileBirthDayView.text = user.birthDay ?: EMPTY
    }

    companion object {
        fun newInstance(user: User?) = ProfileFragment()
            .apply {
                arguments = Bundle().apply {
                    putParcelable(USER_MODEL, user)
                }
            }
    }
}
