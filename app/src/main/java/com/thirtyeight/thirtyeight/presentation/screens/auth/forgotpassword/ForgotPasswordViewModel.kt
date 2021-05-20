package com.thirtyeight.thirtyeight.presentation.screens.auth.forgotpassword

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
class ForgotPasswordViewModel(
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<ForgotPasswordViewState, ForgotPasswordUiAction, ForgotPasswordWish>(coroutineContextProvider, stringResourceMapper) {

    val emailInputId by lazy { GenerateId() }

    private val emailValidationRule = EmailFormatValidationRule()

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() =
            ForgotPasswordViewState(
                    form = Form(
                            listOf(
                                    InputState(emailInputId, "", true),
                            )
                    ),
                    sendButtonEnabled = false,
                    showSuccess = false
            )

    override fun processUiAction(uiAction: ForgotPasswordUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is ForgotPasswordUiAction.InputTextChanged -> {
                reduce(ForgotPasswordWish.ChangeInput(uiAction.id, uiAction.text))
                checkForButtonEnable(viewState.form)
                postViewState()
            }
            ForgotPasswordUiAction.SendClicked -> {
                if (validateForm()) {
                    reduceAndPost(ForgotPasswordWish.ShowSuccess)
                }
            }
            ForgotPasswordUiAction.CloseClicked -> {
                _navigationLiveData.postValue(NavigateTo.Finish)
            }
        }
    }

    override val reducer: Reducer<ForgotPasswordViewState, ForgotPasswordWish>
        get() = ForgotPasswordReducer()

    private class ForgotPasswordReducer : Reducer<ForgotPasswordViewState, ForgotPasswordWish> {
        override fun invoke(
                viewState: ForgotPasswordViewState,
                wish: ForgotPasswordWish
        ): ForgotPasswordViewState {
            return when (wish) {
                is ForgotPasswordWish.ChangeInput -> {
                    viewState.copy(form = viewState.form.updateInputText(wish.id, wish.text))
                }
                is ForgotPasswordWish.EnableSendButton -> {
                    viewState.copy(sendButtonEnabled = wish.enabled)
                }
                ForgotPasswordWish.ClearErrors -> {
                    viewState.copy(form = viewState.form.clearErrors())
                }
                is ForgotPasswordWish.ShowErrorOnInput -> {
                    viewState.copy(form = viewState.form.showError(wish.id, wish.textRes))
                }
                ForgotPasswordWish.ShowSuccess -> {
                    viewState.copy(showSuccess = true)
                }
            }
        }
    }

    private fun checkForButtonEnable(form: Form) {
        val enable = form.inputs.all { it.text.isNotEmpty() }
        if (viewState.sendButtonEnabled != enable)
            reduce(ForgotPasswordWish.EnableSendButton(enable))
        reduce(ForgotPasswordWish.ClearErrors)
    }

    private fun validateForm(): Boolean {
        val emailInput = viewState.form.getInputById(emailInputId)
        if (!emailValidationRule.validate(emailInput.text)) {
            reduceAndPost(ForgotPasswordWish.ShowErrorOnInput(emailInputId, R.string.incorrect_email_format))
            return false
        }
        return true
    }
}