package com.thirtyeight.thirtyeight.presentation.screens.mechanics.tinder

import androidx.lifecycle.LiveData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.tinder.TinderItemEntity
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.tinder.CheckTinderAnswerUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.tinder.GetTinderDataUseCase
import com.thirtyeight.thirtyeight.domain.valueobjects.TinderType
import com.thirtyeight.thirtyeight.presentation.ext.exhaustive
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */

class TinderViewModel(
        private val items: List<TinderItemEntity>,
        private val getTinderDataUseCase: GetTinderDataUseCase,
        private val checkTinderAnswerUseCase: CheckTinderAnswerUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<TinderViewState, TinderUiAction, TinderWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override val reducer: Reducer<TinderViewState, TinderWish>
        get() = TinderReducer()

    private var tries = 0

    override fun getInitialViewState(): TinderViewState = TinderViewState(
            items,
            0,
            0
    )

    override fun processUiAction(uiAction: TinderUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is TinderUiAction.AnswerChosen -> {
                answerChosen(uiAction.type)
            }
        }.exhaustive
    }

    inner class TinderReducer : Reducer<TinderViewState, TinderWish> {
        override fun invoke(viewState: TinderViewState, wish: TinderWish): TinderViewState {
            return when (wish) {
                TinderWish.IncrementPoint -> {
                    viewState.copy(points = viewState.points + 1)
                }
                TinderWish.IncrementCurrentQuestionNumber -> {
                    viewState.copy(currentQuestionNumber = viewState.currentQuestionNumber + 1)
                }
                is TinderWish.DataLoaded -> {
                    viewState.copy(list = wish.list)
                }
            }
        }
    }

    private fun answerChosen(type: TinderType) {
        tries++
        val list = viewState.list
        val currentPosition = viewState.currentQuestionNumber
        val isRight = checkTinderAnswerUseCase.execute(
                CheckTinderAnswerUseCase.Answer(
                        list[currentPosition],
                        type
                )
        )
        if (isRight) {
            reduce(TinderWish.IncrementPoint)
        }
        if (currentPosition == list.size - 1) {
            _navigationLiveData.postValue(NavigateTo.Result(viewState.points, items.size))
        } else {
            reduceAndPost(TinderWish.IncrementCurrentQuestionNumber)
        }
    }

    companion object {

        fun createInstance(args: TinderFragmentArgs) =
                TinderFragment().apply {
                    arguments = args.toBundle()
                }
    }
}