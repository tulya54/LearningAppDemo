package com.thirtyeight.thirtyeight.presentation.screens.main.profile.editprofile

import androidx.lifecycle.LiveData
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/22/21.
 */
class EditProfileViewModel(
    coroutineContextProvider: CoroutineContextProvider,
    stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<EditProfileViewState, EditProfileUiAction, EditProfileWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() =
        EditProfileViewState(tmp = "")

    override val reducer: Reducer<EditProfileViewState, EditProfileWish>
        get() = EditProfileReducer()

    override fun onCreate() {
        super.onCreate()
    }

    override fun processUiAction(uiAction: EditProfileUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
        }
    }

    private class EditProfileReducer : Reducer<EditProfileViewState, EditProfileWish> {
        override fun invoke(viewState: EditProfileViewState, wish: EditProfileWish): EditProfileViewState {
            return viewState
        }
    }
}