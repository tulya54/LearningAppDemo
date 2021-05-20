package com.thirtyeight.thirtyeight.presentation.screens.auth.register.steptwo

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
sealed class NavigateTo {

    class LastStep(val email: String, val password: String) : NavigateTo()
}