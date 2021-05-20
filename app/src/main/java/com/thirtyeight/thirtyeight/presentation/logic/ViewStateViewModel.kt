package com.thirtyeight.thirtyeight.presentation.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
typealias Reducer<VS, WISH> = (viewState: VS, wish: WISH) -> VS

abstract class ViewStateViewModel<VS : ViewState, UI_ACTION, WISH>(
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : BaseViewModel(coroutineContextProvider, stringResourceMapper) {

    abstract fun getInitialViewState(): VS
    abstract val reducer: Reducer<VS, WISH>

    private val _viewStateLiveData = MutableLiveData<VS>()
    val viewStateLiveData: LiveData<VS> = _viewStateLiveData

    protected lateinit var viewState: VS

    override fun onCreate() {
        super.onCreate()
        viewState = getInitialViewState()
        postViewState()
    }

    protected fun reduceAndPost(wish: WISH) {
        reduce(wish)
        postViewState()
    }

    protected fun reduce(wish: WISH) {
        viewState = reducer.invoke(viewState, wish)
    }

    protected fun postViewState() {
        _viewStateLiveData.postValue(viewState)
    }

    open fun processUiAction(uiAction: UI_ACTION) {
    }
}