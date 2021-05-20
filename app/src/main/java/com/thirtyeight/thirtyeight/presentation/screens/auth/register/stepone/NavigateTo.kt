package com.thirtyeight.thirtyeight.presentation.screens.auth.register.stepone

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
sealed class NavigateTo {

    class StepTwo(val fullName: String, val birthDate: String) : NavigateTo()
}