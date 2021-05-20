package com.thirtyeight.thirtyeight.presentation.screens.auth.register.stepone

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
sealed class RegisterStepOneUiAction {

    class InputTextChanged(val id: Long, val text: String) : RegisterStepOneUiAction()
    object NextClicked : RegisterStepOneUiAction()
}