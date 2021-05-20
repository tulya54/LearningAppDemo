package com.thirtyeight.thirtyeight.presentation.screens.main.profile.changepassword

import androidx.lifecycle.LiveData
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/20/21.
 */
class ChangePasswordViewModel(
    coroutineContextProvider: CoroutineContextProvider,
    stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<ChangePasswordViewState, ChangePasswordUiAction, ChangePasswordWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() =
        ChangePasswordViewState(tmp = "")

    override val reducer: Reducer<ChangePasswordViewState, ChangePasswordWish>
        get() = ChangePasswordReducer()

    override fun onCreate() {
        super.onCreate()
    }

    override fun processUiAction(uiAction: ChangePasswordUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
        }
    }

    private class ChangePasswordReducer : Reducer<ChangePasswordViewState, ChangePasswordWish> {
        override fun invoke(viewState: ChangePasswordViewState, wish: ChangePasswordWish): ChangePasswordViewState {
            return viewState
        }
    }
}