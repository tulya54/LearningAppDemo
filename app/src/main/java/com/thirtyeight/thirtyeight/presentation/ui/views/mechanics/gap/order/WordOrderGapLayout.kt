package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.order

import android.content.Context
import android.util.AttributeSet
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.WordOrderGapOptionData
import com.thirtyeight.thirtyeight.util.drag.DropListener

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class WordOrderGapLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : OrderGapLayout<WordOrderGapOptionData, WordOrderGapItemLayout>(context, attrs, defStyleAttr) {

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

    override fun setDataToGap(gap: WordOrderGapItemLayout, data: WordOrderGapOptionData) =
            gap.setData(data.text)
}