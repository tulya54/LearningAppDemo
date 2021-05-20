package com.thirtyeight.thirtyeight.presentation.screens.main.mytopics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thirtyeight.thirtyeight.data.model.MyTopicsMockDto
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
class MyTopicsViewModel(
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<MyTopicsViewState, MyTopicsUiAction, MyTopicsWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    private val _allTopics = MutableLiveData<List<MyTopicsMockDto>>()
    val allTopics: LiveData<List<MyTopicsMockDto>> = _allTopics

    fun setAllTopics() {
        val data = listOf(
            MyTopicsMockDto("Very Long Topic title", 0),
            MyTopicsMockDto("Topic title", 5),
            MyTopicsMockDto("Long Topic title", 3),
            MyTopicsMockDto("Long Topic title", 3)
        )

        _allTopics.value = data
    }

    override fun getInitialViewState() =
            MyTopicsViewState(tmp = "")

    override val reducer: Reducer<MyTopicsViewState, MyTopicsWish>
        get() = MyTopicsReducer()

    override fun onCreate() {
        super.onCreate()
    }

    override fun processUiAction(uiAction: MyTopicsUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
        }
    }

    private class MyTopicsReducer : Reducer<MyTopicsViewState, MyTopicsWish> {
        override fun invoke(viewState: MyTopicsViewState, wish: MyTopicsWish): MyTopicsViewState {
            return viewState
        }
    }
}