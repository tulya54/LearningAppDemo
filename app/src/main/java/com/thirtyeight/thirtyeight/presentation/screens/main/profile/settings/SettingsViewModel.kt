package com.thirtyeight.thirtyeight.presentation.screens.main.profile.settings

import androidx.lifecycle.LiveData
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
class SettingsViewModel(
    coroutineContextProvider: CoroutineContextProvider,
    stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<SettingsViewState, SettingsUiAction, SettingsWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() =
        SettingsViewState(tmp = "")

    override val reducer: Reducer<SettingsViewState, SettingsWish>
        get() = ProfileReducer()

    override fun onCreate() {
        super.onCreate()
    }

    override fun processUiAction(uiAction: SettingsUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            SettingsUiAction.ChangePasswordClicked -> {
                _navigationLiveData.postValue(NavigateTo.ChangePassword)
            }
        }
    }

    private class ProfileReducer : Reducer<SettingsViewState, SettingsWish> {
        override fun invoke(viewState: SettingsViewState, wish: SettingsWish): SettingsViewState {
            return viewState
        }
    }
}