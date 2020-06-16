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
import com.semashko.profile.data.entities.User
import com.semashko.profile.presentation.ProfileUiState
import com.semashko.profile.presentation.viewmodels.ProfileViewModel
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by lifecycleScope.viewModel(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        fun newInstance() = ProfileFragment()
    }
}
