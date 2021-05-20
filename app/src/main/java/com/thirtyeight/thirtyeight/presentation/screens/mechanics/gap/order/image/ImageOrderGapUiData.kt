package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.image

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.ImageOrderGapOptionData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.OrderGapData
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/10/21.
 */
data class ImageOrderGapUiData(
        val question: GapQuestionEntity<OrderGapData, ImageOrderGapOptionData>
) : Serializable