package com.thirtyeight.thirtyeight.presentation.screens.auth.register.steptwo

import androidx.lifecycle.LiveData
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.logic.Form
import com.thirtyeight.thirtyeight.presentation.logic.InputState
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.presentation.logic.validation.EmailFormatValidationRule
import com.thirtyeight.thirtyeight.presentation.logic.validation.PasswordsMatchValidationRule
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.GenerateId
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class RegisterStepTwoViewModel(
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<RegisterStepTwoViewState, RegisterStepTwoUiAction, RegisterStepTwoWish>(coroutineContextProvider, stringResourceMapper) {

    val emailInputId by lazy { GenerateId() }
    val passwordInputId by lazy { GenerateId() }
    val repeatPasswordInputId by lazy { GenerateId() }

    private val emailValidationRule = EmailFormatValidationRule()
    private val passwordsMatchValidationRule = PasswordsMatchValidationRule()

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() =
            RegisterStepTwoViewState(
                    form = Form(
                            listOf(
                                    InputState(emailInputId, "", true),
                                    InputState(passwordInputId, "", true),
                                    InputState(repeatPasswordInputId, "", true),
                            )
                    ),
                    nextButtonEnabled = false
            )

    override val reducer: Reducer<RegisterStepTwoViewState, RegisterStepTwoWish>
        get() = RegisterStepTwoReducer()

    override fun processUiAction(uiAction: RegisterStepTwoUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is RegisterStepTwoUiAction.InputTextChanged -> {
                reduce(RegisterStepTwoWish.ChangeInput(uiAction.id, uiAction.text))
                checkForButtonEnable(viewState.form)
                postViewState()
            }
            RegisterStepTwoUiAction.NextClicked -> {
                if (validateForm()) {
                    _navigationLiveData.postValue(
                            NavigateTo.LastStep(
                                    viewState.form.getInputById(emailInputId).text,
                                    viewState.form.getInputById(passwordInputId).text,
                            )
                    )
                }
            }
        }
    }

    private class RegisterStepTwoReducer : Reducer<RegisterStepTwoViewState, RegisterStepTwoWish> {
        override fun invoke(
                viewState: RegisterStepTwoViewState,
                wish: RegisterStepTwoWish
        ): RegisterStepTwoViewState {
            return when (wish) {
                is RegisterStepTwoWish.ChangeInput -> {
                    viewState.copy(form = viewState.form.updateInputText(wish.id, wish.text))
                }
                is RegisterStepTwoWish.EnableNextButton -> {
                    viewState.copy(nextButtonEnabled = wish.enabled)
                }
                RegisterStepTwoWish.ClearErrors -> {
                    viewState.copy(form = viewState.form.clearErrors())
                }
                is RegisterStepTwoWish.ShowErrorOnInput -> {
                    viewState.copy(form = viewState.form.showError(wish.id, wish.textRes))
                }
            }
        }
    }

    private fun checkForButtonEnable(form: Form) {
        val enable = form.inputs.all { it.text.isNotEmpty() }
        if (viewState.nextButtonEnabled != enable)
            reduce(RegisterStepTwoWish.EnableNextButton(enable))
        reduce(RegisterStepTwoWish.ClearErrors)
    }

    private fun validateForm(): Boolean {
        val emailInput = viewState.form.getInputById(emailInputId)
        val passwordInput = viewState.form.getInputById(passwordInputId)
        val repeatPasswordInput = viewState.form.getInputById(repeatPasswordInputId)
        if (!emailValidationRule.validate(emailInput.text)) {
            reduceAndPost(RegisterStepTwoWish.ShowErrorOnInput(emailInputId, R.string.incorrect_email_format))
            return false
        } else if (!passwordsMatchValidationRule.validate(passwordInput.text to repeatPasswordInput.text)) {
            reduce(RegisterStepTwoWish.ShowErrorOnInput(passwordInputId, 0))
            reduceAndPost(RegisterStepTwoWish.ShowErrorOnInput(repeatPasswordInputId, R.string.passwords_dont_match))
            return false
        }
        return true
    }
}