package com.thirtyeight.thirtyeight.presentation.screens.mechanics.captcha

/**
 * Created by nikolozakhvlediani on 3/31/21.
 */
sealed class CaptchaUiAction {

    class ItemClicked(val itemId: Long) : CaptchaUiAction()
    object CheckClicked : CaptchaUiAction()
}