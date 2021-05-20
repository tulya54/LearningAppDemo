package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.capture

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 3/22/21.
 */
class CaptureCounterLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val countTextVew: TextView
    private val imageView: ImageView

    private var viewState = ViewState(0, 0)

    init {
        val view = context.inflateLayout(R.layout.layout_capture_counter, this, true)
        countTextVew = view.findViewById(R.id.text_view)
        imageView = view.findViewById(R.id.image_view)
    }

    fun setCount(count: Int) {
        viewState = viewState.copy(count = count)
        invalidateViewState()
    }

    fun setImageRes(imageRes: Int) {
        viewState = viewState.copy(imageRes = imageRes)
        invalidateViewState()
    }

    private fun invalidateViewState() {
        with(viewState) {
            countTextVew.text = count.toString()
            imageView.setImageResource(imageRes)
        }
    }

    private data class ViewState(
            val count: Int,
            val imageRes: Int
    )
}