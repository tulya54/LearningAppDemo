package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.order

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.nex3z.flowlayout.FlowLayout
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.OrderGapData
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.GapLayout

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
abstract class OrderGapLayout<GapOptionData, GapItem : OrderGapItemLayout<*, *>> @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GapLayout<GapItem, OrderGapData, GapOptionData>(context, attrs, defStyleAttr) {

    override val gapDimensions: Dimensions by lazy {
        Dimensions(
                100.asPx,
                45.asPx
        )
    }

    override fun addGaps(
            gapData: OrderGapData,
            clickListener: (gapView: GapItem, index: Int) -> Unit
    ) {
        val layout = context.inflateLayout(R.layout.part_order_gaps)
        val titleTextView = layout.findViewById<TextView>(R.id.title_text_view)
        val flowLayout = layout.findViewById<FlowLayout>(R.id.flow_layout)
        titleTextView.text = gapData.text
        var counter = 0
        (0 until gapData.gapCount).forEach {
            val index = counter
            val gap = createGapView().apply {
                setOnClickListener {
                    clickListener(this, index)
                }
                clearGap(this)
            }
            counter++
            gaps.add(gap)
            availableGaps.add(true)
            flowLayout.addView(
                    gap,
                    LinearLayout.LayoutParams(
                            gapDimensions.width,
                            gapDimensions.height
                    )
            )
        }
        gapContainer.addView(
                layout,
                LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply
                {
                    gravity = Gravity.CENTER
                }
        )
    }
}