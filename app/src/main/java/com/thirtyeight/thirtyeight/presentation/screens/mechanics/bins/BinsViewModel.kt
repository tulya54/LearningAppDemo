package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins

import androidx.lifecycle.LiveData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinFallingItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinsDataEntity
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
abstract class BinsViewModel<BinData>(
        val data: BinsDataEntity<BinData>,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<BinsViewState<BinData>, BinsUiAction, BinsWish<BinData>>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() = BinsViewState<BinData>(
            bins = emptyList(),
            currentFallingItem = null,
            fallingItems = emptyList(),
            selectedBinIndex = 0,
            points = 0,
            from = 0
    )

    override val reducer: Reducer<BinsViewState<BinData>, BinsWish<BinData>>
        get() = BinsReducer()

    override fun processUiAction(uiAction: BinsUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            BinsUiAction.LeftArrowClicked -> {
                val newIndex = calculateNextIndex(
                        viewState.selectedBinIndex - 1, viewState.bins.size
                )
                if (newIndex != viewState.selectedBinIndex)
                    reduceAndPost(BinsWish.UpdateSelectedBin(newIndex))
            }
            BinsUiAction.RightArrowClicked -> {
                val newIndex = calculateNextIndex(
                        viewState.selectedBinIndex + 1, viewState.bins.size
                )
                if (newIndex != viewState.selectedBinIndex)
                    reduceAndPost(BinsWish.UpdateSelectedBin(newIndex))
            }
            is BinsUiAction.CategoryClicked -> {
                reduceAndPost(BinsWish.UpdateSelectedBin(uiAction.index))
            }
            BinsUiAction.ItemReachedBottom -> {
                if (isRight())
                    reduce(BinsWish.IncrementPoint)
                if (calculateNextItemToSend() == null) {
                    _navigationLiveData.postValue(
                            NavigateTo.Result(
                                    viewState.points,
                                    viewState.from
                            )
                    )
                } else {
                    reduceAndPost(BinsWish.SendNextItem)
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        reduceAndPost(BinsWish.DataLoaded(data))
    }

    private inner class BinsReducer : Reducer<BinsViewState<BinData>, BinsWish<BinData>> {
        override fun invoke(viewState: BinsViewState<BinData>, wish: BinsWish<BinData>) =
                when (wish) {
                    is BinsWish.DataLoaded -> {
                        viewState.copy(
                                bins = wish.data.bins,
                                currentFallingItem = wish.data.fallingItems[0],
                                fallingItems = wish.data.fallingItems,
                                selectedBinIndex = 0,
                                from = wish.data.fallingItems.size
                        )
                    }
                    is BinsWish.UpdateSelectedBin -> {
                        viewState.copy(
                                selectedBinIndex = wish.index
                        )
                    }
                    BinsWish.IncrementPoint -> {
                        viewState.copy(
                                points = viewState.points + 1
                        )
                    }
                    BinsWish.SendNextItem -> {
                        viewState.copy(
                                currentFallingItem = calculateNextItemToSend()
                        )
                    }
                }
    }

    private fun calculateNextIndex(index: Int, size: Int): Int {
        var nextIndex = if (index < 0) 0 else index
        nextIndex = if (nextIndex >= size) size - 1 else nextIndex
        return nextIndex
    }

    private fun calculateNextItemToSend(): BinFallingItemEntity<BinData>? {
        var currentIndex = viewState.fallingItems.indexOf(viewState.currentFallingItem)
        currentIndex++
        return viewState.fallingItems.getOrNull(currentIndex)
    }

    private fun isRight(): Boolean {
        return viewState.currentFallingItem?.categoryId == viewState.bins[viewState.selectedBinIndex].id
    }
}