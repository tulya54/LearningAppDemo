package com.thirtyeight.thirtyeight.presentation.screens.auth.forgotpassword

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
sealed class ForgotPasswordWish {

    class ChangeInput(val id: Long, val text: String) : ForgotPasswordWish()
    class EnableSendButton(val enabled: Boolean) : ForgotPasswordWish()
    class ShowErrorOnInput(val id: Long, val textRes: Int) : ForgotPasswordWish()
    object ShowSuccess : ForgotPasswordWish()
    object ClearErrors : ForgotPasswordWish()
}