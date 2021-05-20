package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins

import android.content.Context
import android.util.AttributeSet
import com.thirtyeight.thirtyeight.R

/**
 * Created by nikolozakhvlediani on 3/30/21.
 */
class BinsWordsLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BinsLayout<WordFallingItemBinLayout, String>(context, attrs, defStyleAttr) {

    override val title: String
        get() = context.getString(R.string.words)

    override val fallingDuration: Long
        get() = 2_500L

    override fun createFallingItemLayout() = WordFallingItemBinLayout(context)
}