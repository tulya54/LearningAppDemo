package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import com.nex3z.flowlayout.FlowLayout
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapItem
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapOptionData
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.presentation.ui.CTextView
import com.thirtyeight.thirtyeight.util.drag.DropListener

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
class SentenceGapLayout constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GapLayout<CTextView, SentenceGapData, SentenceGapOptionData>(context, attrs, defStyleAttr) {

    override val gapDimensions: Dimensions by lazy { Dimensions(100.asPx, 45.asPx) }

    private val gapButtonContextWrapper = ContextThemeWrapper(context, R.style.MechGapButtonStyle)
    private val sentenceTextContextWrapper =
            ContextThemeWrapper(context, R.style.MechanicTextViewStyle)

    override fun addGaps(
            gapData: SentenceGapData,
            clickListener: (gapView: CTextView, index: Int) -> Unit
    ) {
        val flowLayout = context.inflateLayout(R.layout.part_sentence_gaps) as FlowLayout
        var counter = 0
        gapData.data.forEach {
            when (it) {
                is SentenceGapItem.Gap -> {
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
                is SentenceGapItem.Word -> {
                    val wordView = CTextView(
                            sentenceTextContextWrapper,
                            null,
                            R.style.MechanicTextViewStyle
                    ).apply {
                        text = it.text
                    }
                    flowLayout.addView(wordView)
                }
            }
        }
        gapContainer.addView(
                flowLayout,
                LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
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

    override fun setDataToGap(gap: CTextView, dataGap: SentenceGapOptionData) {
        gap.text = dataGap.text
    }
}