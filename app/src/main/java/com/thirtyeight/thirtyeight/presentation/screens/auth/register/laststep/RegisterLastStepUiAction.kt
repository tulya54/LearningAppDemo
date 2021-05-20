package com.thirtyeight.thirtyeight.presentation.screens.auth.register.laststep

import com.thirtyeight.thirtyeight.domain.entities.auth.register.RegistrationParams

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
sealed class RegisterLastStepUiAction {

    class NextClicked(val registrationParams: RegistrationParams) : RegisterLastStepUiAction()
}