package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.order

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
abstract class OrderGapItemLayout<OrderGapItemView : View, OrderGapData> @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    abstract fun createView(): OrderGapItemView
    abstract fun setDataToView(gapItemView: OrderGapItemView, data: OrderGapData)

    abstract val dimensions: Pair<Int, Int>

    private val binView: OrderGapItemView

    init {
        val frameLayout = context.inflateLayout(
                R.layout.layout_order_gap_item,
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

    fun setData(data: OrderGapData) {
        setDataToView(binView, data)
    }
}