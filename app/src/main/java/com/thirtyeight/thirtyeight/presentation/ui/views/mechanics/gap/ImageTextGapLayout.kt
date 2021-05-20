package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.nex3z.flowlayout.FlowLayout
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imagetext.ImageTextGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imagetext.ImageTextGapOptionData
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.presentation.ui.CTextView
import com.thirtyeight.thirtyeight.util.drag.DropListener

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
class ImageTextGapLayout constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GapLayout<CTextView, ImageTextGapData, ImageTextGapOptionData>(context, attrs, defStyleAttr) {

    override val gapDimensions: Dimensions by lazy { Dimensions(100.asPx, 45.asPx) }

    private val gapButtonContextWrapper = ContextThemeWrapper(context, R.style.MechGapButtonStyle)

    override fun addGaps(
            gapData: ImageTextGapData,
            clickListener: (gapView: CTextView, index: Int) -> Unit
    ) {
        val view = context.inflateLayout(R.layout.part_image_text_gaps)
        val imageView = view.findViewById<ImageView>(R.id.image_view)
        val flowLayout = view.findViewById<FlowLayout>(R.id.flow_layout)
        imageView.setImageResource(gapData.image)
        (0 until gapData.gapCount).forEach { index ->
            val gap = createGapView().apply {
                setOnClickListener {
                    clickListener(this, index)
                }
                clearGap(this)
            }
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
                view,
                LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER
                }
        )
    }

    override fun createGapView(): CTextView {
        return CTextView(
                gapButtonContextWrapper,
                null,
                R.style.MechGapButtonStyle
        )
    }

    override fun clearGap(gap: CTextView) {
        gap.text = ""
        gap.setOnDragListener(
                DropListener {
                    chooseOptionForGap(
                            gaps.indexOf(gap),
                            optionData[draggedItem]!!,
                            gap
                    )
                }
        )
    }

    override fun setDataToGap(gap: CTextView, data: ImageTextGapOptionData) {
        gap.text = data.text
    }
}