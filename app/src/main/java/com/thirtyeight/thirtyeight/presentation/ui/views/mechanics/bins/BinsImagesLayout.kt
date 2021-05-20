package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins

import android.content.Context
import android.util.AttributeSet
import com.thirtyeight.thirtyeight.R

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
class BinsImagesLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BinsLayout<ImageFallingItemBinLayout, Int>(context, attrs, defStyleAttr) {

    override val title: String
        get() = context.getString(R.string.objects)

    override val fallingDuration: Long
        get() = 2_000L

    override fun createFallingItemLayout() = ImageFallingItemBinLayout(context)
}