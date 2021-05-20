package com.thirtyeight.thirtyeight.presentation.screens.auth.register.stepone

import com.thirtyeight.thirtyeight.presentation.logic.Form
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
data class RegisterStepOneViewState(
        val form: Form,
        val nextButtonEnabled: Boolean
) : ViewState()