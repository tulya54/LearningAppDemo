package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ui.CTextView

/**
 * Created by nikolozakhvlediani on 3/30/21.
 */
class WordFallingItemBinLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FallingItemBinLayout<CTextView, String>(context, attrs, defStyleAttr) {

    override fun createView(): CTextView {
        val contextWrapper = ContextThemeWrapper(context, R.style.MechBinFallingItemTextStyle)
        return CTextView(contextWrapper, null, R.style.MechBinFallingItemTextStyle)
    }

    override fun setDataToView(binView: CTextView, data: String) {
        binView.text = data
    }

    override val dimensions: Pair<Int, Int>
        get() = WRAP_CONTENT to WRAP_CONTENT
}