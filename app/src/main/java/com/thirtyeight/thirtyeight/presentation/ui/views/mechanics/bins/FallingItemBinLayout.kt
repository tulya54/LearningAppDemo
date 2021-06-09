package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
abstract class FallingItemBinLayout<BinView : View, BinData> @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    FrameLayout(context, attrs, defStyleAttr) {

    abstract fun createView(): BinView
    abstract fun setDataToView(binView: BinView, data: BinData)

    abstract val dimensions: Pair<Int, Int>
    private val binView: BinView

    init {
        val frameLayout = context.inflateLayout(
                R.layout.layout_bin_falling_item,
                this,
                true
        ) as FrameLayout
        binView = createView()
        frameLayout.addView(
                binView,
                LayoutParams(dimensions.first, dimensions.second).apply {
                    gravity = Gravity.CENTER
                }
        )
    }

    fun setData(binData: BinData) {
        setDataToView(binView, binData)
    }
}