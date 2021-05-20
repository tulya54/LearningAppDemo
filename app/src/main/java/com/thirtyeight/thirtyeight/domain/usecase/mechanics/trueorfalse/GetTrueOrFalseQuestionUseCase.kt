package com.thirtyeight.thirtyeight.domain.usecase.mechanics.trueorfalse

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaOptionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
class GetTrueOrFalseQuestionUseCase() : BaseUseCase<Unit, TriviaQuestionEntity>() {

    override fun execute(input: Unit): TriviaQuestionEntity {
        val answerTrue = TriviaOptionEntity(GenerateId(), "true")
        return TriviaQuestionEntity(
                R.string.is_this_statement_true_or_false,
                "Is Tokyo the capital of Japan?",
                false,
                listOf(
                        TriviaOptionEntity(GenerateId(), "false"),
                        answerTrue
                ),
                listOf(answerTrue.id),
                -1L
        )
    }
}