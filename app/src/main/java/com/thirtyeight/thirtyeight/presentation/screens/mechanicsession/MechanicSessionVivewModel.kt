package com.thirtyeight.thirtyeight.presentation.screens.mechanicsession

import androidx.lifecycle.LiveData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.MechanicDataEntity
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/9/21.
 */
class MechanicSessionViewModel(
        private val mechanicSessionData: List<MechanicDataEntity>,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<MechanicSessionViewState, MechanicSessionUiAction, MechanicSessionWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() = MechanicSessionViewState(
            "",
            emptyList(),
            null,
            -1,
            false,
            0,
            0,
            0f,
            true
    )

    override fun onCreate() {
        super.onCreate()
        reduce(MechanicSessionWish.DataLoaded(mechanicSessionData))
        reduceAndPost(MechanicSessionWish.LoadNext)
    }

    override val reducer: Reducer<MechanicSessionViewState, MechanicSessionWish>
        get() = MechanicSessionReducer()

    override fun processUiAction(uiAction: MechanicSessionUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            MechanicSessionUiAction.CloseClicked -> {
                closeClicked()
            }
            is MechanicSessionUiAction.NavigateToResult -> {
                reduceAndPost(
                        MechanicSessionWish.ShowResult(
                                uiAction.points,
                                uiAction.from,
                                viewState.currentIndex != viewState.mechanicSessionData.size - 1
                        )
                )
            }
            MechanicSessionUiAction.LoadNextClicked -> {
                if (viewState.currentIndex != viewState.mechanicSessionData.size - 1) {
                    reduceAndPost(MechanicSessionWish.LoadNext)
                }
            }
            MechanicSessionUiAction.RetryClicked -> {
                reduceAndPost(MechanicSessionWish.Retry)
            }
        }
    }

    private inner class MechanicSessionReducer :
            Reducer<MechanicSessionViewState, MechanicSessionWish> {

        override fun invoke(
                viewState: MechanicSessionViewState,
                wish: MechanicSessionWish
        ): MechanicSessionViewState {
            return when (wish) {
                is MechanicSessionWish.DataLoaded -> {
                    viewState.copy(
                            mechanicSessionData = wish.mechanics
                    )
                }
                MechanicSessionWish.LoadNext -> {
                    val newIndex = viewState.currentIndex + 1
                    val entity = viewState.mechanicSessionData[newIndex]
                    viewState.copy(
                            title = entity.title,
                            currentIndex = newIndex,
                            resultShown = false,
                            currentStep = entity,
                            progress = newIndex.toFloat() / viewState.mechanicSessionData.size
                    )
                }
                is MechanicSessionWish.ShowResult -> {
                    viewState.copy(
                            resultShown = true,
                            from = wish.from,
                            points = wish.points,
                            showNext = wish.showNext
                    )
                }
                MechanicSessionWish.Retry -> {
                    viewState.copy(
                            resultShown = false
                    )
                }
            }
        }
    }

    private fun closeClicked() {
        _navigationLiveData.postValue(NavigateTo.Finish)
    }
}