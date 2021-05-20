package com.thirtyeight.thirtyeight.presentation.screens.auth

import com.thirtyeight.thirtyeight.domain.entities.auth.register.RegistrationParams
import com.thirtyeight.thirtyeight.presentation.logic.BaseViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
class AuthViewModel(
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : BaseViewModel(coroutineContextProvider, stringResourceMapper) {

    var registrationParams = RegistrationParams(
            email = "",
            password = "",
            avatar = "",
            birthDate = "",
            name = ""
    )
}