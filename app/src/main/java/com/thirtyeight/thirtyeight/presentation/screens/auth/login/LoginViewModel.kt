package com.thirtyeight.thirtyeight.presentation.screens.auth.login

import androidx.lifecycle.LiveData
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.logic.Form
import com.thirtyeight.thirtyeight.presentation.logic.InputState
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.presentation.logic.validation.EmailFormatValidationRule
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.GenerateId
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class LoginViewModel(
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<LoginViewState, LoginUiAction, LoginWish>(coroutineContextProvider, stringResourceMapper) {

    val emailInputId by lazy { GenerateId() }
    val passwordInputId by lazy { GenerateId() }

    private val emailValidationRule = EmailFormatValidationRule()

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() =
            LoginViewState(
                    form = Form(
                            listOf(
                                    InputState(emailInputId, "", true),
                                    InputState(passwordInputId, "", true)
                            )
                    ),
                    loginButtonEnabled = false
            )

    override val reducer: Reducer<LoginViewState, LoginWish>
        get() = LoginReducer()

    override fun processUiAction(uiAction: LoginUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is LoginUiAction.InputTextChanged -> {
                reduce(LoginWish.ChangeInput(uiAction.id, uiAction.text))
                checkForButtonEnable(viewState.form)
                postViewState()
            }
            LoginUiAction.ForgotPasswordClicked -> {
                _navigationLiveData.postValue(NavigateTo.ForgotPassword)
            }
            LoginUiAction.LoginClicked -> {
                if (validateForm()) {
                    _navigationLiveData.postValue(NavigateTo.App)
                }
            }
        }
    }

    private class LoginReducer : Reducer<LoginViewState, LoginWish> {
        override fun invoke(viewState: LoginViewState, wish: LoginWish): LoginViewState {
            return when (wish) {
                is LoginWish.ChangeInput -> {
                    viewState.copy(form = viewState.form.updateInputText(wish.id, wish.text))
                }
                is LoginWish.EnableLogin -> {
                    viewState.copy(loginButtonEnabled = wish.enabled)
                }
                is LoginWish.ShowErrorOnInput -> {
                    viewState.copy(form = viewState.form.showError(wish.id, wish.textRes))
                }
                LoginWish.WrongCredentials -> {
                    viewState.copy(form = viewState.form.showErrorOnFullForm(R.string.wrong_credentials))
                }
                LoginWish.ClearErrors -> {
                    viewState.copy(form = viewState.form.clearErrors())
                }
            }
        }
    }

    private fun checkForButtonEnable(form: Form) {
        val enable = form.inputs.all { it.text.isNotEmpty() }
        if (viewState.loginButtonEnabled != enable)
            reduce(LoginWish.EnableLogin(enable))
        reduce(LoginWish.ClearErrors)
    }

    private fun validateForm(): Boolean {
        val emailInput = viewState.form.getInputById(emailInputId)
        if (!emailValidationRule.validate(emailInput.text)) {
            reduceAndPost(LoginWish.ShowErrorOnInput(emailInputId, R.string.incorrect_email_format))
            return false
        }
        return true
    }
}