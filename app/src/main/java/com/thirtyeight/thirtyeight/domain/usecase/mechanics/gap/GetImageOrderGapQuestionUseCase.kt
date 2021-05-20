package com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapOptionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.ImageOrderGapOptionData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.OrderGapData
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class GetImageOrderGapQuestionUseCase :
        BaseUseCase<Unit, GapQuestionEntity<OrderGapData, ImageOrderGapOptionData>>() {

    override fun execute(input: Unit): GapQuestionEntity<OrderGapData, ImageOrderGapOptionData> {
        val butterfly = GenerateId()
        val hamster = GenerateId()
        val cow = GenerateId()
        return GapQuestionEntity(
                R.string.put_images_in_correct_order,
                OrderGapData("From smallest to biggest", 3),
                listOf(
                        GapOptionEntity(butterfly, ImageOrderGapOptionData(R.drawable.butterfly)),
                        GapOptionEntity(hamster, ImageOrderGapOptionData(R.drawable.hamster)),
                        GapOptionEntity(cow, ImageOrderGapOptionData(R.drawable.cow)),
                ).shuffled(),
                listOf(butterfly, hamster, cow)
        )
    }
}