package com.thirtyeight.thirtyeight.presentation.screens.auth.register.steptwo

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
sealed class RegisterStepTwoUiAction {

    class InputTextChanged(val id: Long, val text: String) : RegisterStepTwoUiAction()
    object NextClicked : RegisterStepTwoUiAction()
}