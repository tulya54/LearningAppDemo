package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imageimage

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage.ImageImageGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage.ImageImageGapOptionData
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/10/21.
 */
data class ImageImageGapUiData(
        val question: GapQuestionEntity<ImageImageGapData, ImageImageGapOptionData>
) : Serializable