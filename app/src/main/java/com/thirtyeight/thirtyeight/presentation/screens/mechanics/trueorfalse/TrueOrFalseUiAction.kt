package com.thirtyeight.thirtyeight.presentation.screens.mechanics.trueorfalse

/**
 * Created by nikolozakhvlediani on 4/1/21.
 */
sealed class TrueOrFalseUiAction {

    class AnswerChosen(val answerId: Long) : TrueOrFalseUiAction()
}