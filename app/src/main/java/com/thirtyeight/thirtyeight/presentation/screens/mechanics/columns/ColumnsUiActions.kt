package com.thirtyeight.thirtyeight.presentation.screens.mechanics.columns

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
sealed class ColumnsUiActions {

    class OptionChosen(val optionId: Long) : ColumnsUiActions()
    object CheckClicked : ColumnsUiActions()
}