package com.thirtyeight.thirtyeight.domain.usecase.mechanics.trivia

import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
class CheckTriviaAnswersUseCase() :
        BaseUseCase<CheckTriviaAnswersUseCase.TriviaAnswers, CheckTriviaAnswersUseCase.TriviaResult>() {

    override fun execute(input: TriviaAnswers): TriviaResult {
        var points = 0
        input.triviaAnswers.forEach {
            if (input.triviaQuestion.rightAnswers.contains(it))
                points++
        }
        return TriviaResult(points, input.triviaQuestion.rightAnswers.size)
    }

    data class TriviaAnswers(
            val triviaQuestion: TriviaQuestionEntity,
            val triviaAnswers: List<Long>
    )

    data class TriviaResult(
            val point: Int,
            val from: Int
    )
}