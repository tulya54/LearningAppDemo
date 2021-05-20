package com.thirtyeight.thirtyeight.presentation.screens.mechanics.trueorfalse

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.trivia.CheckTriviaAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.trueorfalse.GetTrueOrFalseQuestionUseCase
import com.thirtyeight.thirtyeight.presentation.ext.exhaustive
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by nikolozakhvlediani on 4/1/21.
 */
class TrueOrFalseViewModel(
        private val trueOrFalseQuestionEntity: TriviaQuestionEntity,
        private val getTrueOrFalseQuestionUseCase: GetTrueOrFalseQuestionUseCase,
        private val checkTriviaAnswersUseCase: CheckTriviaAnswersUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<TrueOrFalseViewState, TrueOrFalseUiAction, TrueOrFalseWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() = TrueOrFalseViewState(
            trueOrFalseQuestionEntity
    )

    override val reducer: Reducer<TrueOrFalseViewState, TrueOrFalseWish>
        get() = TrueOrFalseReducer()

    override fun processUiAction(uiAction: TrueOrFalseUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is TrueOrFalseUiAction.AnswerChosen -> {
                checkAnswers(uiAction.answerId)
            }
        }.exhaustive
    }

    private inner class TrueOrFalseReducer : Reducer<TrueOrFalseViewState, TrueOrFalseWish> {
        override fun invoke(
                viewState: TrueOrFalseViewState,
                wish: TrueOrFalseWish
        ): TrueOrFalseViewState {
            return when (wish) {
                is TrueOrFalseWish.DataLoaded -> viewState.copy(
                        question = wish.question
                )
            }
        }
    }

    private fun checkAnswers(answerId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val answers = checkTriviaAnswersUseCase.execute(
                    CheckTriviaAnswersUseCase.TriviaAnswers(
                            viewState.question!!,
                            listOf(answerId)
                    )
            )
            withContext(Dispatchers.Main) {
                _navigationLiveData.postValue(NavigateTo.Result(answers.point, answers.from))
            }
        }
    }
}