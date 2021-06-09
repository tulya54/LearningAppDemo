package com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
class CheckGapAnswersUseCase: BaseUseCase<CheckGapAnswersUseCase.GapAnswers, CheckGapAnswersUseCase.GapResult>() {

    override fun execute(input: GapAnswers): GapResult {
        val resultList = ArrayList<Boolean>()
        val from = input.gaps.size
        var point = 0
        input.gapQuestionEntity.answers.forEachIndexed { index, answerId ->
            if (answerId == input.gaps[index]) {
                point++
                resultList.add(true)
            } else {
                resultList.add(false)
            }
        }
        return GapResult(point, from, resultList)
    }

    data class GapAnswers(
            val gapQuestionEntity: GapQuestionEntity<*, *>,
            val gaps: List<Long>
    )

    data class GapResult(
            val point: Int,
            val from: Int,
            val resultList: List<Boolean>
    )
}