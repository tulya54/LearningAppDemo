package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.order

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.nikoloz14.myextensions.asPx

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ImageOrderGapItemLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : OrderGapItemLayout<AppCompatImageView, Int>(context, attrs, defStyleAttr) {

    override val dimensions: Pair<Int, Int>
        get() = 24.asPx to 24.asPx

    override fun createView(): AppCompatImageView =
            AppCompatImageView(context)

    override fun setDataToView(gapItemView: AppCompatImageView, data: Int) {
        if (data == -1) {
            gapItemView.setImageDrawable(null)
        } else {
            gapItemView.setImageResource(data)
        }
    }
}