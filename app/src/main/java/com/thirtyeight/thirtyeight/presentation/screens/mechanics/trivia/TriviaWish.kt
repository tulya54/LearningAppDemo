package com.thirtyeight.thirtyeight.presentation.screens.mechanics.trivia

import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
sealed class TriviaWish {

    class DataLoaded(val question: TriviaQuestionEntity) : TriviaWish()
    class ChooseAnswer(val answerId: Long) : TriviaWish()
    class UnChooseAnswer(val answerId: Long) : TriviaWish()
}