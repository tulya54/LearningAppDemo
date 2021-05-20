package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 3/22/21.
 */
class MechanicsToolbarLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val closeButton: View
    private val progressBar: ProgressBar
    private val titleTextView: TextView
    private val viewStub: FrameLayout

    private var viewState = ViewState("", 0f)

    var closeClicked: (() -> Unit)? = null

    init {
        val view = context.inflateLayout(R.layout.layout_mechanics_toolbar, this, true)
        closeButton = view.findViewById(R.id.close_button)
        progressBar = view.findViewById(R.id.progress_bar)
        titleTextView = view.findViewById(R.id.title_text_view)
        viewStub = view.findViewById(R.id.view_stub)
        closeButton.setOnClickListener {
            closeClicked?.invoke()
        }
    }

    fun setTitle(title: String) {
        viewState = viewState.copy(title = title)
        invalidateViewState()
    }

    fun updateProgress(progress: Float) {
        viewState = viewState.copy(progress = progress)
        invalidateViewState()
    }

    fun addViewToStub(view: View) {
        viewStub.addView(view)
    }

    private fun invalidateViewState() {
        with(viewState) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                progressBar.setProgress((progress * 100).toInt(), true)
            } else {
                progressBar.progress = (progress * 100).toInt()
            }
            titleTextView.text = title
        }
    }

    private data class ViewState(
            val title: String,
            val progress: Float
    )
}