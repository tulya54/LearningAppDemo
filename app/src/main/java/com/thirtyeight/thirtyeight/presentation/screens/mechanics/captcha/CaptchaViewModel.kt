package com.thirtyeight.thirtyeight.presentation.screens.mechanics.captcha

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thirtyeight.thirtyeight.domain.entities.mechanics.captcha.CaptchaQuestionEntity
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.captcha.CheckCaptchaAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.captcha.GetCaptchaQuestionUseCase
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by nikolozakhvlediani on 3/31/21.
 */
class CaptchaViewModel(
        private val captchaQuestionEntity: CaptchaQuestionEntity,
        private val getCaptchaQuestionUseCase: GetCaptchaQuestionUseCase,
        private val checkCaptchaAnswersUseCase: CheckCaptchaAnswersUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<CaptchaViewState, CaptchaUiAction, CaptchaWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() = CaptchaViewState(
            "",
            emptyList(),
            emptyList(),
            emptyList()
    )

    override val reducer: Reducer<CaptchaViewState, CaptchaWish>
        get() = CaptchaReducer()

    override fun onCreate() {
        super.onCreate()
        reduceAndPost(CaptchaWish.DataLoaded(captchaQuestionEntity))
    }

    override fun processUiAction(uiAction: CaptchaUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is CaptchaUiAction.ItemClicked -> {
                if (viewState.selectedOptions.contains(uiAction.itemId)) {
                    reduceAndPost(CaptchaWish.DeselectItem(uiAction.itemId))
                } else {
                    reduceAndPost(CaptchaWish.SelectItem(uiAction.itemId))
                }
            }
            CaptchaUiAction.CheckClicked -> {
                checkAnswers()
            }
        }
    }

    private inner class CaptchaReducer : Reducer<CaptchaViewState, CaptchaWish> {
        override fun invoke(
                viewState: CaptchaViewState,
                wish: CaptchaWish
        ): CaptchaViewState {
            return when (wish) {
                is CaptchaWish.DataLoaded -> {
                    viewState.copy(
                            questionText = wish.captchaQuestionEntity.text,
                            data = wish.captchaQuestionEntity.options,
                            rightAnswers = wish.captchaQuestionEntity.rightAnswers
                    )
                }
                is CaptchaWish.DeselectItem -> {
                    val list = viewState.selectedOptions.toMutableList()
                    list.remove(wish.itemId)
                    viewState.copy(selectedOptions = list)
                }
                is CaptchaWish.SelectItem -> {
                    val list = viewState.selectedOptions.toMutableList()
                    list.add(wish.itemId)
                    viewState.copy(selectedOptions = list)
                }
            }
        }
    }

    private fun checkAnswers() {
        viewModelScope.launch(Dispatchers.IO) {
            val answers = checkCaptchaAnswersUseCase.execute(
                    CheckCaptchaAnswersUseCase.CaptchaAnswers(
                            viewState.rightAnswers,
                            viewState.selectedOptions
                    )
            )
            withContext(Dispatchers.Main) {
                _navigationLiveData.postValue(NavigateTo.Result(answers.point, answers.from))
            }
        }
    }
}