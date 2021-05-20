package com.thirtyeight.thirtyeight.presentation.screens.auth.forgotpassword

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
sealed class ForgotPasswordUiAction {

    class InputTextChanged(val id: Long, val text: String) : ForgotPasswordUiAction()
    object SendClicked : ForgotPasswordUiAction()
    object CloseClicked : ForgotPasswordUiAction()
}