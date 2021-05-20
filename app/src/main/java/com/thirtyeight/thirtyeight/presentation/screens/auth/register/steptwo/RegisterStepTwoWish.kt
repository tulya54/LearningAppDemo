package com.thirtyeight.thirtyeight.presentation.screens.auth.register.steptwo

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
sealed class RegisterStepTwoWish {

    class ChangeInput(val id: Long, val text: String) : RegisterStepTwoWish()
    class EnableNextButton(val enabled: Boolean) : RegisterStepTwoWish()
    class ShowErrorOnInput(val id: Long, val textRes: Int) : RegisterStepTwoWish()
    object ClearErrors : RegisterStepTwoWish()
}