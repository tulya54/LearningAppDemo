package com.thirtyeight.thirtyeight.presentation.screens.mechanics.columns

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thirtyeight.thirtyeight.domain.entities.mechanics.columns.ColumnsQuestionEntity
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.columns.CheckColumnsAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.columns.GetColumnsQuestionUseCase
import com.thirtyeight.thirtyeight.presentation.logic.Reducer
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ColumnsViewModel(
        private val columnsQuestionEntity: ColumnsQuestionEntity,
        private val getColumnsQuestionUseCase: GetColumnsQuestionUseCase,
        private val checkColumnsAnswersUseCase: CheckColumnsAnswersUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : ViewStateViewModel<ColumnsViewState, ColumnsUiActions, ColumnsWish>(coroutineContextProvider, stringResourceMapper) {

    private val _navigationLiveData = SingleLifeLiveData<NavigateTo>()
    val navigationLiveData: LiveData<NavigateTo> = _navigationLiveData

    override fun getInitialViewState() = ColumnsViewState(
            null,
            emptyList(),
            emptyList(),
            emptyList(),
            null,
            null
    )

    override val reducer: Reducer<ColumnsViewState, ColumnsWish>
        get() = ColumnsReducer()

    override fun onCreate() {
        super.onCreate()
        reduceAndPost(ColumnsWish.DataLoaded(columnsQuestionEntity))
    }

    override fun processUiAction(uiAction: ColumnsUiActions) {
        super.processUiAction(uiAction)
        when (uiAction) {
            ColumnsUiActions.CheckClicked -> {
                checkAnswers()
            }
            is ColumnsUiActions.OptionChosen -> {
                val previousItem = viewState.selectedItem
                val previousSelectedColumn = viewState.selectedColumnIndex
                val columnIndex = getIndexOfColumnOfItem(uiAction.optionId)
                alreadySelected(uiAction.optionId, columnIndex)?.let {
                    reduceAndPost(ColumnsWish.DeselectPair(it.first, it.second))
                } ?: kotlin.run {
                    previousItem?.let {
                        if (columnIndex == previousSelectedColumn) {
                            reduce(ColumnsWish.DeselectOption)
                            reduceAndPost(ColumnsWish.SelectOption(uiAction.optionId, columnIndex))
                        } else {
                            val pair = if (columnIndex == 0)
                                uiAction.optionId to it
                            else
                                it to uiAction.optionId
                            reduce(ColumnsWish.DeselectOption)
                            reduceAndPost(ColumnsWish.SelectPair(pair.first, pair.second))
                        }
                    } ?: kotlin.run {
                        reduceAndPost(
                                ColumnsWish.SelectOption(
                                        uiAction.optionId,
                                        getIndexOfColumnOfItem(uiAction.optionId)
                                )
                        )
                    }
                }
            }
        }
    }

    private inner class ColumnsReducer : Reducer<ColumnsViewState, ColumnsWish> {

        override fun invoke(viewState: ColumnsViewState, wish: ColumnsWish): ColumnsViewState {
            return when (wish) {
                is ColumnsWish.DataLoaded -> {
                    viewState.copy(
                            question = wish.question,
                            columnOne = wish.question.firstColumn,
                            columnTwo = wish.question.secondColumn
                    )
                }
                ColumnsWish.DeselectOption -> {
                    viewState.copy(
                            selectedItem = null,
                            selectedColumnIndex = null
                    )
                }
                is ColumnsWish.DeselectPair -> {
                    val selectedPairs = viewState.selectedPairs.toMutableList()
                    selectedPairs.remove(wish.first to wish.second)
                    viewState.copy(
                            selectedPairs = selectedPairs
                    )
                }
                is ColumnsWish.SelectOption -> {
                    viewState.copy(
                            selectedItem = wish.optionId,
                            selectedColumnIndex = wish.columnIndex
                    )
                }
                is ColumnsWish.SelectPair -> {
                    val selectedPairs = viewState.selectedPairs.toMutableList()
                    selectedPairs.add(wish.first to wish.second)
                    viewState.copy(
                            selectedPairs = selectedPairs
                    )
                }
            }
        }
    }

    private fun getIndexOfColumnOfItem(id: Long) =
            if (viewState.columnOne.any { it.id == id })
                0
            else
                1

    private fun alreadySelected(id: Long, indexOfColumn: Int): Pair<Long, Long>? {
        return viewState.selectedPairs.firstOrNull {
            val item = if (indexOfColumn == 0) it.first else it.second
            return@firstOrNull item == id
        }
    }

    private fun checkAnswers() {
        viewModelScope.launch(Dispatchers.IO) {
            val answers = checkColumnsAnswersUseCase.execute(
                    CheckColumnsAnswersUseCase.ColumnAnswers(
                            viewState.question?.rightAnswers ?: emptyList(),
                            viewState.selectedPairs
                    )
            )
            withContext(Dispatchers.Main) {
                _navigationLiveData.postValue(
                        NavigateTo.Result(
                                answers.point,
                                answers.from
                        )
                )
            }
        }
    }
}