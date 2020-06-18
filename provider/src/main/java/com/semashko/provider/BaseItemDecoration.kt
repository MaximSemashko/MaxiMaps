package com.semashko.provider

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BaseItemDecoration(
    private val verticalMargin: Int? = null,
    private val horizontalMargin: Int? = null
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            left = horizontalMargin ?: 0
            right = horizontalMargin ?: 0
            top = verticalMargin ?: 0
//            bottom = verticalMargin ?: 0
        }
    }
}