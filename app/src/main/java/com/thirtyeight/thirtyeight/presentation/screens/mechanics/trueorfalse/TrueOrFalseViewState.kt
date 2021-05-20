package com.thirtyeight.thirtyeight.presentation.screens.mechanics.trueorfalse

import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 4/1/21.
 */
data class TrueOrFalseViewState(
        val question: TriviaQuestionEntity?
) : ViewState()