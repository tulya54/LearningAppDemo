package com.thirtyeight.thirtyeight.presentation.screens.auth.register.stepone

import androidx.lifecycle.LiveData
import com.thirtyeight.thirtyeight.presentation.logic.Form
import com.thirtyeight.thirtyeight.presentation.logic.InputState
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.GenerateId
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class RegisterStepOneViewModel(
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<RegisterStepOneViewState, RegisterStepOneUiAction, RegisterStepOneWish>(coroutineContextProvider, stringResourceMapper) {

    val fullNameInputId by lazy { GenerateId() }
    val birthDateInputId by lazy { GenerateId() }
    val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.US)

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() =
            RegisterStepOneViewState(
                    form = Form(
                            listOf(
                                    InputState(fullNameInputId, "", true),
                                    InputState(birthDateInputId, "", true)
                            )
                    ),
                    nextButtonEnabled = false
            )

    override val reducer: Reducer<RegisterStepOneViewState, RegisterStepOneWish>
        get() = RegisterStepOneReducer()

    override fun processUiAction(uiAction: RegisterStepOneUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is RegisterStepOneUiAction.InputTextChanged -> {
                reduce(RegisterStepOneWish.ChangeInput(uiAction.id, uiAction.text))
                checkForButtonEnable(viewState.form)
                postViewState()
            }
            RegisterStepOneUiAction.NextClicked -> {
                _navigationLiveData.postValue(
                        NavigateTo.StepTwo(
                                viewState.form.getInputById(fullNameInputId).text,
                                viewState.form.getInputById(birthDateInputId).text
                        )
                )
            }
        }
    }

    private class RegisterStepOneReducer : Reducer<RegisterStepOneViewState, RegisterStepOneWish> {
        override fun invoke(
                viewState: RegisterStepOneViewState,
                wish: RegisterStepOneWish
        ): RegisterStepOneViewState {
            return when (wish) {
                is RegisterStepOneWish.ChangeInput -> {
                    viewState.copy(form = viewState.form.updateInputText(wish.id, wish.text))
                }
                RegisterStepOneWish.ClearErrors -> {
                    viewState.copy(form = viewState.form.clearErrors())
                }
                is RegisterStepOneWish.EnableNextButton -> {
                    viewState.copy(nextButtonEnabled = wish.enabled)
                }
                is RegisterStepOneWish.ShowErrorOnInput -> {
                    viewState.copy(form = viewState.form.showError(wish.id, wish.textRes))
                }
            }
        }
    }

    private fun checkForButtonEnable(form: Form) {
        val enable = form.inputs.all { it.text.isNotEmpty() }
        if (viewState.nextButtonEnabled != enable)
            reduce(RegisterStepOneWish.EnableNextButton(enable))
        reduce(RegisterStepOneWish.ClearErrors)
    }
}