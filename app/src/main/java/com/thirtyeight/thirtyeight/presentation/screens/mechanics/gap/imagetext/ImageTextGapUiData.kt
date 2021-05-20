package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imagetext

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imagetext.ImageTextGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imagetext.ImageTextGapOptionData
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/10/21.
 */
data class ImageTextGapUiData(
        val question: GapQuestionEntity<ImageTextGapData, ImageTextGapOptionData>
) : Serializable