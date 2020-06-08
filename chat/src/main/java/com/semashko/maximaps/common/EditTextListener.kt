package com.semashko.maximaps.common

import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton

class EditTextListener(private val btn: ImageButton) : TextWatcher {

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        TransitionManager.beginDelayedTransition(btn.rootView as ViewGroup)
        if (charSequence.isEmpty()) {
            btn.visibility = View.INVISIBLE
        } else {
            btn.visibility = View.VISIBLE
        }
    }

    override fun afterTextChanged(editable: Editable) {

    }
}
