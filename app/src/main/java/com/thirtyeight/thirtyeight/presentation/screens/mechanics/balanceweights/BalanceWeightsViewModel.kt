package com.thirtyeight.thirtyeight.presentation.screens.mechanics.balanceweights

import androidx.lifecycle.LiveData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.balanceweight.BalanceWeightItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.balanceweight.BalanceWeightQuestionEntity
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.balanceweight.GetBalanceWeightQuestionUseCase
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/3/21.
 */
class BalanceWeightsViewModel(
        private val balanceWeightsQuestionEntity: BalanceWeightQuestionEntity,
        private val getBalanceWeightQuestionUseCase: GetBalanceWeightQuestionUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<BalanceWeightsViewState, BalanceWeightsUiAction, BalanceWeightsWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() = BalanceWeightsViewState(
            "",
            BalanceWeightItemEntity(0, "", 0),
            BalanceWeightItemEntity(0, "", 0),
            0,
            0,
            0
    )

    override val reducer: Reducer<BalanceWeightsViewState, BalanceWeightsWish>
        get() = BalanceWeightsReducer()

    override fun onCreate() {
        super.onCreate()
        reduceAndPost(BalanceWeightsWish.DataLoaded(balanceWeightsQuestionEntity))
    }

    override fun processUiAction(uiAction: BalanceWeightsUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            BalanceWeightsUiAction.DecreaseClicked -> {
                val currentWeight = viewState.rightItem.weight
                val newWeight = (currentWeight - viewState.step).coerceAtLeast(0)
                reduceAndPost(BalanceWeightsWish.ChangeWeight(newWeight))
            }
            BalanceWeightsUiAction.IncreaseClicked -> {
                val currentWeight = viewState.rightItem.weight
                val newWeight = currentWeight + viewState.step
                if (newWeight == viewState.answer) {
                    _navigationLiveData.postValue(NavigateTo.Result(1, 1))
                } else {
                    reduceAndPost(BalanceWeightsWish.ChangeWeight(newWeight))
                }
            }
        }
    }

    private class BalanceWeightsReducer : Reducer<BalanceWeightsViewState, BalanceWeightsWish> {
        override fun invoke(
                viewState: BalanceWeightsViewState,
                wish: BalanceWeightsWish
        ): BalanceWeightsViewState {
            return when (wish) {
                is BalanceWeightsWish.DataLoaded -> {
                    viewState.copy(
                            title = wish.question.text,
                            leftItem = wish.question.itemOne,
                            rightItem = wish.question.itemTwo,
                            step = wish.question.increaseCount,
                            answer = wish.question.answer,
                            initialWeightRight = wish.question.itemTwo.weight
                    )
                }
                is BalanceWeightsWish.ChangeWeight -> {
                    viewState.copy(
                            rightItem = viewState.rightItem.copy(
                                    weight = wish.weight
                            )
                    )
                }
            }
        }
    }
}