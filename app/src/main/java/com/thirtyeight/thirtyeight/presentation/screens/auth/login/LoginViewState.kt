package com.thirtyeight.thirtyeight.presentation.screens.auth.login

import com.thirtyeight.thirtyeight.presentation.logic.Form
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
data class LoginViewState(
        val form: Form,
        val loginButtonEnabled: Boolean
) : ViewState()