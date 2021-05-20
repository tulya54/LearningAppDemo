package com.thirtyeight.thirtyeight.domain.usecase.mechanics.trivia

import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity
import com.thirtyeight.thirtyeight.domain.repository.MechanicsRepository
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
class GetTriviaQuestionUseCase(
        private val mechanicsRepository: MechanicsRepository
) : BaseUseCase<Unit, TriviaQuestionEntity>() {

    override fun execute(input: Unit): TriviaQuestionEntity =
            mechanicsRepository.getTriviaQuestion()
}