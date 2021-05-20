package com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapOptionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage.ImageImageGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage.ImageImageGapOptionData
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 3/28/21.
 */
class GetImageImageGapQuestionUseCase :
        BaseUseCase<Unit, GapQuestionEntity<ImageImageGapData, ImageImageGapOptionData>>() {

    override fun execute(input: Unit): GapQuestionEntity<ImageImageGapData, ImageImageGapOptionData> {
        val zero = GenerateId()
        val four = GenerateId()
        return GapQuestionEntity(
                R.string.fill_image_gaps,
                ImageImageGapData(
                        R.drawable.leaves,
                        9,
                        listOf(0, 4)
                ),
                listOf(
                        GapOptionEntity(GenerateId(), ImageImageGapOptionData(R.drawable.leaves_7)),
                        GapOptionEntity(zero, ImageImageGapOptionData(R.drawable.leaves_1)),
                        GapOptionEntity(four, ImageImageGapOptionData(R.drawable.leaves_5)),
                ),
                listOf(zero, four)
        )
    }
}