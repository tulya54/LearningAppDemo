package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
sealed class GapUiAction {

    class OptionChosen(val optionId: Long, val gapIndex: Int) : GapUiAction()
    class GapClicked(val gapIndex: Int) : GapUiAction()
    object CheckClicked : GapUiAction()
}