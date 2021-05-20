package com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapOptionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapItem
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapOptionData
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
class GetSentenceGapQuestionUseCase :
        BaseUseCase<Unit, GapQuestionEntity<SentenceGapData, SentenceGapOptionData>>() {

    override fun execute(input: Unit): GapQuestionEntity<SentenceGapData, SentenceGapOptionData> {
        val be = GenerateId()
        val to = GenerateId()
        val question = GenerateId()
        return GapQuestionEntity(
                R.string.fill_sentence_gaps,
                SentenceGapData(
                        listOf(
                                SentenceGapItem.Word("To"),
                                SentenceGapItem.Gap,
                                SentenceGapItem.Word("or not"),
                                SentenceGapItem.Gap,
                                SentenceGapItem.Word("be "),
                                SentenceGapItem.Word("- that is the"),
                                SentenceGapItem.Gap
                        )
                ),
                listOf(
                        GapOptionEntity(GenerateId(), SentenceGapOptionData("Eat")),
                        GapOptionEntity(GenerateId(), SentenceGapOptionData("Sleep")),
                        GapOptionEntity(question, SentenceGapOptionData("Question")),
                        GapOptionEntity(to, SentenceGapOptionData("To")),
                        GapOptionEntity(be, SentenceGapOptionData("Be")),
                        GapOptionEntity(GenerateId(), SentenceGapOptionData("Answer")),
                ),
                listOf(be, to, question)
        )
    }
}