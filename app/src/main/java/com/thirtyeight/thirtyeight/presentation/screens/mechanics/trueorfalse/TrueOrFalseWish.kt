package com.thirtyeight.thirtyeight.presentation.screens.mechanics.trueorfalse

import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity

/**
 * Created by nikolozakhvlediani on 4/1/21.
 */
sealed class TrueOrFalseWish {

    class DataLoaded(val question: TriviaQuestionEntity) : TrueOrFalseWish()
}