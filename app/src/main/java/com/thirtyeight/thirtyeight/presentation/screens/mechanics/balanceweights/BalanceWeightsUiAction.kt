package com.thirtyeight.thirtyeight.presentation.screens.mechanics.balanceweights

/**
 * Created by nikolozakhvlediani on 4/3/21.
 */
sealed class BalanceWeightsUiAction {

    object IncreaseClicked : BalanceWeightsUiAction()
    object DecreaseClicked : BalanceWeightsUiAction()
}