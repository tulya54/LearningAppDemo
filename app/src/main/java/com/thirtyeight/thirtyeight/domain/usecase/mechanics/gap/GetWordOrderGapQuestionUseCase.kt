package com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapOptionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.OrderGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.WordOrderGapOptionData
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class GetWordOrderGapQuestionUseCase :
        BaseUseCase<Unit, GapQuestionEntity<OrderGapData, WordOrderGapOptionData>>() {

    override fun execute(input: Unit): GapQuestionEntity<OrderGapData, WordOrderGapOptionData> {
        val its = GenerateId()
        val me = GenerateId()
        val mario = GenerateId()
        return GapQuestionEntity(
                R.string.put_words_in_correct_order,
                OrderGapData("It's Me, Mario", 3),
                listOf(
                        GapOptionEntity(its, WordOrderGapOptionData("It's")),
                        GapOptionEntity(me, WordOrderGapOptionData("Me")),
                        GapOptionEntity(mario, WordOrderGapOptionData("Mario")),
                ).shuffled(),
                listOf(its, me, mario)
        )
    }
}