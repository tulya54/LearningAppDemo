package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.order

import android.content.Context
import android.util.AttributeSet
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.ImageOrderGapOptionData
import com.thirtyeight.thirtyeight.util.drag.DropListener

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ImageOrderGapLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : OrderGapLayout<ImageOrderGapOptionData, ImageOrderGapItemLayout>(context, attrs, defStyleAttr) {

    override fun createGapView(): ImageOrderGapItemLayout =
            ImageOrderGapItemLayout(context)

    override fun clearGap(gap: ImageOrderGapItemLayout) {
        gap.setData(-1)
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

    override fun setDataToGap(gap: ImageOrderGapItemLayout, data: ImageOrderGapOptionData) =
            gap.setData(data.image)
}