package com.thirtyeight.thirtyeight.presentation.screens.auth.login

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
sealed class NavigateTo {

    object App : NavigateTo()
    object ForgotPassword : NavigateTo()
}