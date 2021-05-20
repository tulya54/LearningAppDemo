package com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.appearingcapture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.AppearingCaptureItemEntity
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.appearingcapture.CheckAppearingCaptureAnswerUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.appearingcapture.GetAppearingCaptureDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.appearingcapture.GetNextAppearingCaptureItemUseCase
import com.thirtyeight.thirtyeight.domain.valueobjects.AppearingCaptureType
import com.thirtyeight.thirtyeight.presentation.ext.exhaustive
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
class AppearingCaptureViewModel(
        val items: List<AppearingCaptureItemEntity>,
        private val getAppearingCaptureDataUseCase: GetAppearingCaptureDataUseCase,
        private val getNextAppearingCaptureItemUseCase: GetNextAppearingCaptureItemUseCase,
        private val checkAppearingCaptureAnswerUseCase: CheckAppearingCaptureAnswerUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<AppearingCaptureViewState, AppearingCaptureUiAction, AppearingCaptureWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    private val _wrongAnswerLiveData = MutableLiveData<AppearingCaptureItemEntity>()
    val wrongAnswerLiveData: LiveData<AppearingCaptureItemEntity> = _wrongAnswerLiveData

    override val reducer: Reducer<AppearingCaptureViewState, AppearingCaptureWish>
        get() = AppearingCaptureReducer()

    override fun getInitialViewState(): AppearingCaptureViewState =
            AppearingCaptureViewState(
                    emptyList(),
                    0,
                    0,
                    TIME * 1_000L
            )

    override fun onCreate() {
        super.onCreate()
        reduceAndPost(AppearingCaptureWish.DataLoaded(items))
        startTimer()
        startRemoveTimer()
    }

    override fun processUiAction(uiAction: AppearingCaptureUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is AppearingCaptureUiAction.AnswerChosen -> {
                answerChosen(uiAction.appearingCaptureItemEntity)
            }
        }.exhaustive
    }

    inner class AppearingCaptureReducer : Reducer<AppearingCaptureViewState, AppearingCaptureWish> {
        override fun invoke(
                viewState: AppearingCaptureViewState,
                wish: AppearingCaptureWish
        ): AppearingCaptureViewState {
            return when (wish) {
                is AppearingCaptureWish.UpdateTime -> {
                    viewState.copy(timeLeft = wish.timeLeft)
                }
                AppearingCaptureWish.IncrementPoint -> {
                    viewState.copy(points = viewState.points + 1)
                }
                is AppearingCaptureWish.DataLoaded -> {
                    viewState.copy(list = wish.list)
                }
                AppearingCaptureWish.IncrementTries -> {
                    viewState.copy(tries = viewState.tries + 1)
                }
            }
        }
    }

    private fun startTimer() {
        startTicker(TIME, 1000L) {
            reduceAndPost(
                    AppearingCaptureWish.UpdateTime(
                            timeLeft = (TIME - it - 1) * 1000L
                    )
            )
            if (it == TIME - 1)
                _navigationLiveData.postValue(
                        NavigateTo.Result(
                                viewState.points,
                                viewState.tries
                        )
                )
        }
    }

    private fun startRemoveTimer() {
        startTicker(TIME * 10, 100L) {
            val currentTime = System.currentTimeMillis()
            val newList = viewState.list.toMutableList()
            val removedList = viewState.list.filter { currentTime > it.removeTime }
            removedList.forEach {
                if (it.appearingCaptureType == AppearingCaptureType.BUTTERFLY) {
                    reduce(AppearingCaptureWish.IncrementTries)
                }
                newList.remove(it)
            }
            (removedList.indices).forEach { _ ->
                val newItem = getNextAppearingCaptureItemUseCase.execute(Unit)
                newList.add(newItem)
            }
            reduceAndPost(AppearingCaptureWish.DataLoaded(newList))
        }
    }

    private fun answerChosen(appearingCaptureItemEntity: AppearingCaptureItemEntity) {
        val list = viewState.list
        val isRight =
                checkAppearingCaptureAnswerUseCase.execute(
                        CheckAppearingCaptureAnswerUseCase.Answer(
                                appearingCaptureItemEntity,
                                AppearingCaptureType.BUTTERFLY
                        )
                )
        val newList = list.toMutableList()
        if (isRight) {
            newList.remove(appearingCaptureItemEntity)
            newList.add(getNextAppearingCaptureItemUseCase.execute(Unit))
            reduce(AppearingCaptureWish.IncrementTries)
            reduce(AppearingCaptureWish.IncrementPoint)
            reduceAndPost(AppearingCaptureWish.DataLoaded(newList))
        } else {
            _wrongAnswerLiveData.postValue(appearingCaptureItemEntity)
        }
    }

    companion object {

        private const val TIME = 30
    }
}