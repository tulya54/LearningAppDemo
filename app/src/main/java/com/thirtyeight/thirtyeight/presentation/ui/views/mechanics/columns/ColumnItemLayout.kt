package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.columns

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.getColorFromAttr
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ColumnItemLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val textView: TextView

    private var viewState = ViewState("", ColumnItemState.NA)
    private var previousViewState: ViewState = viewState

    init {
        val view = context.inflateLayout(R.layout.layout_column_item, this, true)
        textView = view.findViewById(R.id.text_view)
    }

    private fun invalidateViewState() {
        with(viewState) {
            if (text != previousViewState.text) {
                textView.text = text
            }
            if (state != previousViewState.state) {
                updateUiFromState(state)
            }
            previousViewState = this
        }
    }

    fun setText(text: String) {
        viewState = viewState.copy(text = text)
        invalidateViewState()
    }

    fun setState(state: ColumnItemState) {
        viewState = viewState.copy(state = state)
        invalidateViewState()
    }

    private fun updateUiFromState(state: ColumnItemState) {
        when (state) {
            ColumnItemState.NONE -> {
                updateTextView(
                        R.drawable.background_mech_button_with_primary_variant_borders,
                        context.getColorFromAttr(R.attr.colorPrimary)
                )
            }
            ColumnItemState.SELECTED -> {
                updateTextView(
                        R.drawable.background_bin_falling_item,
                        context.getColorFromAttr(R.attr.colorPrimary)
                )
            }
            ColumnItemState.DISABLED -> {
                updateTextView(
                        R.drawable.background_mech_button_grey,
                        context.getColorFromAttr(R.attr.colorOnBackground)
                )
            }
            ColumnItemState.NA -> {
            }
        }
    }

    private fun updateTextView(background: Int, textColor: Int) {
        setBackgroundResource(background)
        textView.setTextColor(textColor)
    }

    private data class ViewState(
            val text: String,
            val state: ColumnItemState
    )
}