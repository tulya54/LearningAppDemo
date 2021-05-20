package com.thirtyeight.thirtyeight.presentation.screens.main.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
class DiscoverViewModel(
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<DiscoverViewState, DiscoverUiAction, DiscoverWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    private val _discoverTopics = MutableLiveData<List<String>>()
    val discoverTopics: LiveData<List<String>> = _discoverTopics

    fun setDiscoverTopics() {
        val data = listOf(
            "Very Long Topic title",
            "Topic title",
            "Long Topic title",
            "Long Topic title"
        )

        _discoverTopics.value = data
    }

    override fun getInitialViewState() =
            DiscoverViewState(tmp = "")

    override val reducer: Reducer<DiscoverViewState, DiscoverWish>
        get() = DiscoverReducer()

    override fun onCreate() {
        super.onCreate()
    }

    override fun processUiAction(uiAction: DiscoverUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
        }
    }

    private class DiscoverReducer : Reducer<DiscoverViewState, DiscoverWish> {
        override fun invoke(viewState: DiscoverViewState, wish: DiscoverWish): DiscoverViewState {
            return viewState
        }
    }
}