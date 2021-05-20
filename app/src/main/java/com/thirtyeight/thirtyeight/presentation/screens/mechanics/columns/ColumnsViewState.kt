package com.thirtyeight.thirtyeight.presentation.screens.mechanics.columns

import com.thirtyeight.thirtyeight.domain.entities.mechanics.columns.ColumnOptionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.columns.ColumnsQuestionEntity
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
data class ColumnsViewState(
        val question: ColumnsQuestionEntity?,
        val columnOne: List<ColumnOptionEntity>,
        val columnTwo: List<ColumnOptionEntity>,
        val selectedPairs: List<Pair<Long, Long>>,
        val selectedItem: Long?,
        val selectedColumnIndex: Int?,
) : ViewState()