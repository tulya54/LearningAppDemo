package com.thirtyeight.thirtyeight.presentation.screens.auth.register.stepone

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
sealed class RegisterStepOneWish {

    class ChangeInput(val id: Long, val text: String) : RegisterStepOneWish()
    class EnableNextButton(val enabled: Boolean) : RegisterStepOneWish()
    class ShowErrorOnInput(val id: Long, val textRes: Int) : RegisterStepOneWish()
    object ClearErrors : RegisterStepOneWish()
}