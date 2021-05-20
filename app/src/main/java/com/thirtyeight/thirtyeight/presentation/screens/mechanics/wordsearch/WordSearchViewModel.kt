package com.thirtyeight.thirtyeight.presentation.screens.mechanics.wordsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.wordsearch.WordSearchDataEntity
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.wordsearch.GenerateWordSearchMatrixUseCase
import com.thirtyeight.thirtyeight.domain.valueobjects.Coordinate
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
class WordSearchViewModel(
        private val wordSearchDataEntity: WordSearchDataEntity,
        private val generateWordSearchMatrixUseCase: GenerateWordSearchMatrixUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<WordSearchViewState, WordSearchUiAction, WordSearchWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    private val _clearSelection = MutableLiveData<List<Coordinate>>()
    val clearSelection: LiveData<List<Coordinate>> = _clearSelection

    override fun getInitialViewState() = WordSearchViewState(
            null,
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList()
    )

    override val reducer: Reducer<WordSearchViewState, WordSearchWish>
        get() = BalanceWeightsReducer()

    override fun onCreate() {
        super.onCreate()
        reduceAndPost(WordSearchWish.DataLoaded(wordSearchDataEntity))
    }

    override fun processUiAction(uiAction: WordSearchUiAction) {
        super.processUiAction(uiAction)
        when (uiAction) {
            is WordSearchUiAction.CheckWord -> {
                val found = viewState.words.contains(uiAction.word)
                if (found) {
                    if (!viewState.lockedInWords.contains(uiAction.word)) {
                        if (viewState.words.size - 1 == viewState.lockedInWords.size) {
                            _navigationLiveData.postValue(
                                    NavigateTo.Result(
                                            viewState.words.size,
                                            viewState.words.size
                                    )
                            )
                        } else {
                            reduceAndPost(
                                    WordSearchWish.WordFound(
                                            uiAction.word,
                                            uiAction.coordinates
                                    )
                            )
                        }
                    }
                } else {
                    _clearSelection.postValue(viewState.lockedInCoordinates)
                }
            }
            is WordSearchUiAction.SelectCoordinates -> {
                reduceAndPost(WordSearchWish.SelectCoordinates(uiAction.coordinates))
            }
        }
    }

    private class BalanceWeightsReducer : Reducer<WordSearchViewState, WordSearchWish> {
        override fun invoke(
                viewState: WordSearchViewState,
                wish: WordSearchWish
        ): WordSearchViewState {
            return when (wish) {
                is WordSearchWish.DataLoaded -> {
                    viewState.copy(
                            matrix = wish.data.matrixEntity,
                            words = wish.data.words
                    )
                }
                is WordSearchWish.WordFound -> {
                    val words = viewState.lockedInWords.toMutableList()
                    val coordinates = viewState.lockedInCoordinates.toMutableList()
                    words.add(wish.word)
                    coordinates.addAll(wish.coordinates)
                    viewState.copy(
                            lockedInWords = words.toSet().toList(),
                            lockedInCoordinates = coordinates.toSet().toList()
                    )
                }
                is WordSearchWish.SelectCoordinates -> {
                    viewState.copy(
                            selectedLetters = wish.coordinates
                    )
                }
            }
        }
    }
}