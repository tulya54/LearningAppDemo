package com.thirtyeight.thirtyeight.presentation.screens.auth.register.getstarted

import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class GetStartedViewModel(
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<GetStartedViewState, GetStartedUiAction, GetStartedWish>(coroutineContextProvider, stringResourceMapper) {

    override fun getInitialViewState() =
            GetStartedViewState(tmp = "")

    override val reducer: Reducer<GetStartedViewState, GetStartedWish>
        get() = GetStartedReducer()

    private class GetStartedReducer : Reducer<GetStartedViewState, GetStartedWish> {
        override fun invoke(
                viewState: GetStartedViewState,
                wish: GetStartedWish
        ): GetStartedViewState {
            return viewState
        }
    }
}