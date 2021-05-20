package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
sealed class BinsUiAction {

    object LeftArrowClicked : BinsUiAction()
    object RightArrowClicked : BinsUiAction()
    class CategoryClicked(val index: Int) : BinsUiAction()
    object ItemReachedBottom : BinsUiAction()
}