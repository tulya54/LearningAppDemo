package com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapOptionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imagetext.ImageTextGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imagetext.ImageTextGapOptionData
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
class GetImageTextGapQuestionUseCase :
        BaseUseCase<Unit, GapQuestionEntity<ImageTextGapData, ImageTextGapOptionData>>() {

    override fun execute(input: Unit): GapQuestionEntity<ImageTextGapData, ImageTextGapOptionData> {
        val boy = GenerateId()
        val girl = GenerateId()
        val tree = GenerateId()
        return GapQuestionEntity(
                R.string.fill_gaps_with_corresponding_words,
                ImageTextGapData(
                        R.drawable.boy_tree_girl,
                        3
                ),
                listOf(
                        GapOptionEntity(GenerateId(), ImageTextGapOptionData("Dog")),
                        GapOptionEntity(boy, ImageTextGapOptionData("Boy")),
                        GapOptionEntity(girl, ImageTextGapOptionData("Girl")),
                        GapOptionEntity(GenerateId(), ImageTextGapOptionData("Ball")),
                        GapOptionEntity(tree, ImageTextGapOptionData("Tree")),
                        GapOptionEntity(GenerateId(), ImageTextGapOptionData("Car")),
                ),
                listOf(boy, tree, girl)
        )
    }
}