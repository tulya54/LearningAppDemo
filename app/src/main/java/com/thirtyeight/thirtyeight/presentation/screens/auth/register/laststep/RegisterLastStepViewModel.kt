package com.thirtyeight.thirtyeight.presentation.screens.auth.register.laststep

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thirtyeight.thirtyeight.domain.DataResult
import com.thirtyeight.thirtyeight.domain.entities.auth.register.RegistrationParams
import com.thirtyeight.thirtyeight.domain.usecase.auth.register.RegisterUseCase
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.presentation.screens.auth.register.laststep.RegisterLastStepUiAction.NextClicked
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class RegisterLastStepViewModel(
        private val registerUseCase: RegisterUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<RegisterLastStepViewState, RegisterLastStepUiAction, RegisterLastStepWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() =
            RegisterLastStepViewState(tmp = "")

    override val reducer: Reducer<RegisterLastStepViewState, RegisterLastStepWish>
        get() = RegistrationLastStepReducer()

    override fun processUiAction(uiAction: RegisterLastStepUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is NextClicked -> {
                register(uiAction.registrationParams)
            }
        }
    }

    private class RegistrationLastStepReducer : Reducer<RegisterLastStepViewState, RegisterLastStepWish> {
        override fun invoke(
                viewState: RegisterLastStepViewState,
                wish: RegisterLastStepWish
        ): RegisterLastStepViewState {
            return viewState
        }
    }

    private fun register(registrationParams: RegistrationParams) {
        viewModelScope.launch {
            when (val result = withContext(contextPool.iO) { registerUseCase.execute(registrationParams) }) {
                is DataResult.Success -> {
                    _navigationLiveData.postValue(NavigateTo.App)
                }
                is DataResult.Error -> {
                    postSimpleError(result.throwable)
                }
            }
        }
    }
}
