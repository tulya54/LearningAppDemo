package com.thirtyeight.thirtyeight.presentation.screens.mechanics.tinder

import com.thirtyeight.thirtyeight.domain.valueobjects.TinderType

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
sealed class TinderUiAction {

    class AnswerChosen(val type: TinderType) : TinderUiAction()
}