package com.thirtyeight.thirtyeight.presentation.screens.auth.login

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
sealed class LoginUiAction {

    class InputTextChanged(val id: Long, val text: String) : LoginUiAction()
    object LoginClicked : LoginUiAction()
    object ForgotPasswordClicked : LoginUiAction()
}