package com.thirtyeight.thirtyeight.presentation.screens.mechanics.trivia

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
sealed class TriviaUiAction {

    class AnswerChosen(val answerId: Long) : TriviaUiAction()
    object CheckClicked : TriviaUiAction()
}