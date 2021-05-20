package com.thirtyeight.thirtyeight.domain.usecase.mechanics.columns

import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
class CheckColumnsAnswersUseCase() :
        BaseUseCase<CheckColumnsAnswersUseCase.ColumnAnswers, CheckColumnsAnswersUseCase.ColumnsResult>() {

    override fun execute(input: ColumnAnswers): ColumnsResult {
        var points = 0
        input.answers.forEach {
            if (input.rightAnswers.contains(it))
                points++
        }
        return ColumnsResult(
                points,
                input.rightAnswers.size
        )
    }

    data class ColumnAnswers(
            val rightAnswers: List<Pair<Long, Long>>,
            val answers: List<Pair<Long, Long>>
    )

    data class ColumnsResult(
            val point: Int,
            val from: Int
    )
}