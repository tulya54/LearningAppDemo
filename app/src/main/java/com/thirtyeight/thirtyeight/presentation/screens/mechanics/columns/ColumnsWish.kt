package com.thirtyeight.thirtyeight.presentation.screens.mechanics.columns

import com.thirtyeight.thirtyeight.domain.entities.mechanics.columns.ColumnsQuestionEntity

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
sealed class ColumnsWish {

    class DataLoaded(val question: ColumnsQuestionEntity) : ColumnsWish()
    class SelectPair(val first: Long, val second: Long) : ColumnsWish()
    class DeselectPair(val first: Long, val second: Long) : ColumnsWish()
    class SelectOption(val optionId: Long, val columnIndex: Int) : ColumnsWish()
    object DeselectOption : ColumnsWish()
}