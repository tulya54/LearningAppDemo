package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.getColorFromAttr
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
class BinCategoryBoxLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val selectedColor = context.getColorFromAttr(R.attr.colorPrimary)
    private val unSelectedColor = context.getColorFromAttr(R.attr.colorOnBackground)

    private val textView: TextView

    private var viewState = ViewState("", false)

    init {
        val view = context.inflateLayout(R.layout.layout_bin_category_box, this, true)
        textView = view.findViewById(R.id.text_view)
    }

    private fun invalidateViewState() {
        with(viewState) {
            textView.text = text
            textView.setTextColor(if (selected) selectedColor else unSelectedColor)
        }
    }

    fun setText(text: String) {
        viewState = viewState.copy(text = text)
        invalidateViewState()
    }

    fun select(selected: Boolean) {
        viewState = viewState.copy(selected = selected)
        invalidateViewState()
    }

    private data class ViewState(
            val text: String,
            val selected: Boolean
    )
}