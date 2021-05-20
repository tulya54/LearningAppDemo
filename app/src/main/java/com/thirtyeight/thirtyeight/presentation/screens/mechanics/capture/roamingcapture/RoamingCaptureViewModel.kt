package com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.roamingcapture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.RoamingCaptureItemEntity
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.roamingcapture.CheckRoamingCaptureAnswerUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.roamingcapture.GetNextRoamingCaptureItemUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.roamingcapture.GetRoamingCaptureDataUseCase
import com.thirtyeight.thirtyeight.domain.valueobjects.RoamingCaptureType
import com.thirtyeight.thirtyeight.presentation.ext.exhaustive
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
class RoamingCaptureViewModel(
        val items: List<RoamingCaptureItemEntity>,
        private val getRoamingCaptureDataUseCase: GetRoamingCaptureDataUseCase,
        private val getNextRoamingCaptureItemUseCase: GetNextRoamingCaptureItemUseCase,
        private val checkRoamingCaptureAnswerUseCase: CheckRoamingCaptureAnswerUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<RoamingCaptureViewState, RoamingCaptureUiAction, RoamingCaptureWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    private val _wrongAnswerLiveData = MutableLiveData<RoamingCaptureItemEntity>()
    val wrongAnswerLiveData: LiveData<RoamingCaptureItemEntity> = _wrongAnswerLiveData

    override val reducer: Reducer<RoamingCaptureViewState, RoamingCaptureWish>
        get() = RoamingCaptureReducer()

    override fun getInitialViewState(): RoamingCaptureViewState =
            RoamingCaptureViewState(
                    emptyList(),
                    0,
                    0,
                    TIME * 1_000L
            )

    override fun onCreate() {
        super.onCreate()
        reduceAndPost(RoamingCaptureWish.DataLoaded(items))
        startTimer()
        startRemoveTimer()
    }

    override fun processUiAction(uiAction: RoamingCaptureUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is RoamingCaptureUiAction.AnswerChosen -> {
                answerChosen(uiAction.roamingCaptureItemEntity)
            }
        }.exhaustive
    }

    inner class RoamingCaptureReducer : Reducer<RoamingCaptureViewState, RoamingCaptureWish> {
        override fun invoke(
                viewState: RoamingCaptureViewState,
                wish: RoamingCaptureWish
        ): RoamingCaptureViewState {
            return when (wish) {
                RoamingCaptureWish.IncrementPoint -> {
                    viewState.copy(points = viewState.points + 1)
                }
                is RoamingCaptureWish.DataLoaded -> {
                    viewState.copy(list = wish.list)
                }
                RoamingCaptureWish.IncrementTries -> {
                    viewState.copy(tries = viewState.tries + 1)
                }
                is RoamingCaptureWish.UpdateTime -> {
                    viewState.copy(timeLeft = wish.time)
                }
            }
        }
    }

    private fun startTimer() {
        startTicker(TIME, 1000L) {
            reduceAndPost(
                    RoamingCaptureWish.UpdateTime(
                            time = (TIME - it - 1) * 1000L
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
                if (it.roamingCaptureType == RoamingCaptureType.BUTTERFLY) {
                    reduce(RoamingCaptureWish.IncrementTries)
                }
                newList.remove(it)
            }
            (removedList.indices).forEach { _ ->
                val newItem = getNextRoamingCaptureItemUseCase.execute(Unit)
                newList.add(newItem)
            }
            reduceAndPost(RoamingCaptureWish.DataLoaded(newList))
        }
    }

    private fun answerChosen(roamingCaptureItemEntity: RoamingCaptureItemEntity) {
        val list = viewState.list
        val isRight =
                checkRoamingCaptureAnswerUseCase.execute(
                        CheckRoamingCaptureAnswerUseCase.Answer(
                                roamingCaptureItemEntity,
                                RoamingCaptureType.BUTTERFLY
                        )
                )
        val newList = list.toMutableList()
        if (isRight) {
            newList.remove(roamingCaptureItemEntity)
            newList.add(getNextRoamingCaptureItemUseCase.execute(Unit))
            reduce(RoamingCaptureWish.IncrementTries)
            reduce(RoamingCaptureWish.IncrementPoint)
            reduceAndPost(RoamingCaptureWish.DataLoaded(newList))
        } else {
            _wrongAnswerLiveData.postValue(roamingCaptureItemEntity)
        }
    }

    companion object {

        private const val TIME = 30
    }
}