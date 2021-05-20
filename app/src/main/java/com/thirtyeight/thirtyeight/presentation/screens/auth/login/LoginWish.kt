package com.thirtyeight.thirtyeight.presentation.screens.auth.login

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
sealed class LoginWish {

    class ChangeInput(val id: Long, val text: String) : LoginWish()
    class EnableLogin(val enabled: Boolean) : LoginWish()
    class ShowErrorOnInput(val id: Long, val textRes: Int) : LoginWish()
    object WrongCredentials : LoginWish()
    object ClearErrors : LoginWish()
}