package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.makeramen.roundedimageview.RoundedImageView
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage.ImageImageGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage.ImageImageGapOptionData
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.util.drag.DropListener

/**
 * Created by nikolozakhvlediani on 3/28/21.
 */
class ImageImageGapLayout(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GapLayout<RoundedImageView, ImageImageGapData, ImageImageGapOptionData>(
        context,
        attrs,
        defStyleAttr
) {

    override val gapDimensions: Dimensions by lazy { Dimensions(100.asPx, 100.asPx) }

    override fun addGaps(
            gapData: ImageImageGapData,
            clickListener: (gapView: RoundedImageView, index: Int) -> Unit
    ) {
        val view = context.inflateLayout(R.layout.part_image_image_gaps) as FrameLayout
        val imageView = view.findViewById<RoundedImageView>(R.id.image_view)
        imageView.setImageResource(gapData.image)
        view.post {
            var counter = 0
            val elemWidth = view.width / 3
            val elemHeight = view.height / 3
            gapData.gapIndexes.forEach {
                val count = counter
                val columnIndex = it / 3
                val rowIndex = it % 3
                val gap = createGapView().apply {
                    setOnClickListener {
                        clickListener(this, count)
                    }
                    clearGap(this)
                }
                counter++
                gaps.add(gap)
                availableGaps.add(true)
                view.addView(
                        gap,
                        LinearLayout.LayoutParams(
                                gapDimensions.width,
                                gapDimensions.height
                        ).apply {
                            leftMargin = elemWidth * rowIndex
                            topMargin = elemHeight * columnIndex
                        }
                )
            }

        }
        gapContainer.addView(
                view,
                LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER
                }
        )
    }

    override fun createGapView(): RoundedImageView =
            RoundedImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                cornerRadius = 10f.asPx.toFloat()
            }

    override fun clearGap(gap: RoundedImageView) {
        gap.setImageResource(R.color.white)
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

    override fun setDataToGap(gap: RoundedImageView, data: ImageImageGapOptionData) {
        gap.setImageResource(data.image)
    }
}