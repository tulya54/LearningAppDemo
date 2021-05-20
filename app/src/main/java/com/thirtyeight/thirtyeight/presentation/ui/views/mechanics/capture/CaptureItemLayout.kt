package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.capture

import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
class CaptureItemLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val root: ViewGroup
    private val imageView: ImageView

    private var viewState = ViewState(0)

    private var transitionHandler: Handler? = null
    private var transitionRunnable: Runnable? = null
    private var reverseTransitionRunnable: Runnable? = null

    init {
        val view = context.inflateLayout(R.layout.layout_capture_item, this, true)
        root = view.findViewById(R.id.root)
        imageView = view.findViewById(R.id.image_view)
    }

    fun setContent(imageRes: Int) {
        viewState = viewState.copy(imageRes = imageRes)
        invalidateViewState()
    }

    fun setWrong() {
        transitionRunnable?.let {
            transitionHandler?.post(it)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        transitionHandler = Handler(Looper.getMainLooper())
        val transitionDrawable = root.background as TransitionDrawable
        reverseTransitionRunnable = Runnable {
            transitionDrawable.reverseTransition(300)
        }
        transitionRunnable = Runnable {
            transitionDrawable.startTransition(300)
            transitionHandler?.postDelayed(reverseTransitionRunnable!!, 400L)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        transitionRunnable?.let {
            transitionHandler?.removeCallbacks(it)
        }
        reverseTransitionRunnable?.let {
            transitionHandler?.removeCallbacks(it)
        }
        transitionRunnable = null
        reverseTransitionRunnable = null
    }

    private fun invalidateViewState() {
        with(viewState) {
            imageView.setImageResource(imageRes)
        }
    }

    private data class ViewState(
            val imageRes: Int
    )
}