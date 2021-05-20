package com.thirtyeight.thirtyeight.presentation.screens.mechanics.trivia

import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
data class TriviaViewState(
        val question: TriviaQuestionEntity?,
        val chosenAnswers: List<Long>
) : ViewState()