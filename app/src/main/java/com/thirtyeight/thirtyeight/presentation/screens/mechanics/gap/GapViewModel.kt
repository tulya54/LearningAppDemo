package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap

import androidx.lifecycle.LiveData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.CheckGapAnswersUseCase
import com.thirtyeight.thirtyeight.presentation.ext.exhaustive
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
abstract class GapViewModel<GapData, OptionData>(
        val gapQuestionEntity: GapQuestionEntity<GapData, OptionData>,
        private val checkGapAnswersUseCase: CheckGapAnswersUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<GapViewState<GapData, OptionData>, GapUiAction, GapWish<GapData, OptionData>>(coroutineContextProvider, stringResourceMapper) {

    abstract fun getGapCount(gapData: GapData): Int

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() = GapViewState<GapData, OptionData>(
            null,
            emptyList()
    )

    override val reducer: Reducer<GapViewState<GapData, OptionData>, GapWish<GapData, OptionData>>
        get() = GapReducer()

    override fun onCreate() {
        super.onCreate()
        reduceAndPost(GapWish.DataLoaded(gapQuestionEntity))
    }

    override fun processUiAction(uiAction: GapUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is GapUiAction.OptionChosen -> {
                reduce(GapWish.ChooseOption(uiAction.optionId, uiAction.gapIndex))
            }
            is GapUiAction.GapClicked -> {
                reduce(GapWish.ClearGap(uiAction.gapIndex))
            }
            GapUiAction.CheckClicked -> {
                checkAnswer()
            }
        }.exhaustive
    }

    private inner class GapReducer :
            Reducer<GapViewState<GapData, OptionData>, GapWish<GapData, OptionData>> {
        override fun invoke(
                viewState: GapViewState<GapData, OptionData>,
                wish: GapWish<GapData, OptionData>
        ): GapViewState<GapData, OptionData> {
            return when (wish) {
                is GapWish.DataLoaded -> viewState.copy(
                        question = wish.question,
                        gaps = MutableList(getGapCount(wish.question.gapData)) { -1 }
                )
                is GapWish.ChooseOption -> {
                    val newGaps = viewState.gaps.toMutableList()
                    newGaps[wish.gapIndex] = wish.optionId
                    viewState.copy(gaps = newGaps)
                }
                is GapWish.ClearGap -> {
                    val newGaps = viewState.gaps.toMutableList()
                    newGaps[wish.index] = -1
                    viewState.copy(gaps = newGaps)
                }
            }
        }
    }

    private fun checkAnswer() {
        viewState.question?.let {
            val result = checkGapAnswersUseCase.execute(
                    CheckGapAnswersUseCase.GapAnswers(
                            it,
                            viewState.gaps
                    )
            )
            _navigationLiveData.postValue(NavigateTo.Result(result.point, result.from))
        }
    }
}