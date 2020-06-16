package com.semashko.profile.presentation.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.semashko.extensions.constants.EMPTY
import com.semashko.provider.navigation.INavigation
import com.semashko.provider.preferences.IUserInfoPreferences
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val EXIT = "EXIT"

class ExitDialogFragment : DialogFragment(), KoinComponent {

    private val userInfoPreferences: IUserInfoPreferences by inject()
    private val navigation: INavigation by inject()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Do you want to exit?")
            .setPositiveButton("Yes") { _, _ ->
                userInfoPreferences.localId = EMPTY
                userInfoPreferences.token = EMPTY
                navigation.openLoginActivity()
            }
            .setNegativeButton(
                "Decline"
            ) { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }

    companion object {
        fun confirmExit(fragmentManager: FragmentManager) {
            val newFragment: DialogFragment = ExitDialogFragment()
            newFragment.show(fragmentManager, EXIT)
        }
    }
}