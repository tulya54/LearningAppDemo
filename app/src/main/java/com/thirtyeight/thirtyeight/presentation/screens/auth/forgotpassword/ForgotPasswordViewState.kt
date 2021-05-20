package com.thirtyeight.thirtyeight.presentation.screens.auth.forgotpassword

import com.thirtyeight.thirtyeight.presentation.logic.Form
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
data class ForgotPasswordViewState(
        val form: Form,
        val sendButtonEnabled: Boolean,
        val showSuccess: Boolean
) : ViewState()