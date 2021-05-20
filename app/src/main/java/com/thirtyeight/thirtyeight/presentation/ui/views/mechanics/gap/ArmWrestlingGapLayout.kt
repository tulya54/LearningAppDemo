package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.armwrestling.ArmWrestlingGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.armwrestling.ArmWrestlingGapOptionData
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.order.WordOrderGapItemLayout
import com.thirtyeight.thirtyeight.util.drag.DropListener

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ArmWrestlingGapLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GapLayout<WordOrderGapItemLayout, ArmWrestlingGapData, ArmWrestlingGapOptionData>(
        context, attrs, defStyleAttr
) {

    override val gapDimensions: Dimensions by lazy {
        Dimensions(
                100.asPx,
                45.asPx
        )
    }

    override fun addGaps(
            gapData: ArmWrestlingGapData,
            clickListener: (gapView: WordOrderGapItemLayout, index: Int) -> Unit
    ) {
        val layout = context.inflateLayout(R.layout.part_arm_wrestling)
        val textOneTextView = layout.findViewById<TextView>(R.id.text_one_text_view)
        val textTwoTextView = layout.findViewById<TextView>(R.id.text_two_text_view)
        val frameLayout = layout.findViewById<FrameLayout>(R.id.frame_layout)

        textOneTextView.text = gapData.wordOne
        textTwoTextView.text = gapData.wordTwo
        val gap = createGapView().apply {
            setOnClickListener {
                clickListener(this, 0)
            }
            clearGap(this)
        }
        gaps.add(gap)
        availableGaps.add(true)
        frameLayout.addView(
                gap,
                LinearLayout.LayoutParams(
                        gapDimensions.width,
                        gapDimensions.height
                )
        )
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

    override fun createGapView(): WordOrderGapItemLayout =
            WordOrderGapItemLayout(context)

    override fun clearGap(gap: WordOrderGapItemLayout) {
        gap.setData("")
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

    override fun setDataToGap(gap: WordOrderGapItemLayout, data: ArmWrestlingGapOptionData) {
        gap.setData(data.text)
    }
}