package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.order

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ui.CTextView

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class WordOrderGapItemLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : OrderGapItemLayout<CTextView, String>(context, attrs, defStyleAttr) {

    override fun createView(): CTextView {
        val contextWrapper = ContextThemeWrapper(context, R.style.MechBinFallingItemTextStyle)
        return CTextView(contextWrapper, null, R.style.MechBinFallingItemTextStyle)
    }

    override fun setDataToView(gapItemView: CTextView, data: String) {
        gapItemView.text = data
    }

    override val dimensions: Pair<Int, Int>
        get() = ViewGroup.LayoutParams.WRAP_CONTENT to ViewGroup.LayoutParams.WRAP_CONTENT
}