package com.thirtyeight.thirtyeight.presentation.screens.mechanics.trivia

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.trivia.CheckTriviaAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.trivia.GetTriviaQuestionUseCase
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
 * Created by nikolozakhvlediani on 3/25/21.
 */
class TriviaViewModel(
        private val triviaQuestionEntity: TriviaQuestionEntity,
        private val getTriviaQuestionUseCase: GetTriviaQuestionUseCase,
        private val checkTriviaAnswersUseCase: CheckTriviaAnswersUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<TriviaViewState, TriviaUiAction, TriviaWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() = TriviaViewState(
            triviaQuestionEntity,
            emptyList()
    )

    override val reducer: Reducer<TriviaViewState, TriviaWish>
        get() = TriviaReducer()

    override fun processUiAction(uiAction: TriviaUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is TriviaUiAction.AnswerChosen -> {
                if (viewState.chosenAnswers.contains(uiAction.answerId)) {
                    reduceAndPost(TriviaWish.UnChooseAnswer(uiAction.answerId))
                } else {
                    reduceAndPost(TriviaWish.ChooseAnswer(uiAction.answerId))
                }
            }
            TriviaUiAction.CheckClicked -> {
                checkAnswers()
            }
        }.exhaustive
    }

    private inner class TriviaReducer : Reducer<TriviaViewState, TriviaWish> {
        override fun invoke(viewState: TriviaViewState, wish: TriviaWish): TriviaViewState {
            return when (wish) {
                is TriviaWish.DataLoaded -> viewState.copy(
                        question = wish.question
                )
                is TriviaWish.ChooseAnswer -> {
                    val list = viewState.chosenAnswers.toMutableList()
                    list.add(wish.answerId)
                    viewState.copy(chosenAnswers = list)
                }
                is TriviaWish.UnChooseAnswer -> {
                    val list = viewState.chosenAnswers.toMutableList()
                    list.remove(wish.answerId)
                    viewState.copy(chosenAnswers = list)
                }
            }
        }
    }

    private fun checkAnswers() {
        viewModelScope.launch(Dispatchers.IO) {
            val answers = checkTriviaAnswersUseCase.execute(
                    CheckTriviaAnswersUseCase.TriviaAnswers(
                            viewState.question!!,
                            viewState.chosenAnswers
                    )
            )
            withContext(Dispatchers.Main) {
                _navigationLiveData.postValue(NavigateTo.Result(answers.point, answers.from))
            }
        }
    }
}