package com.thirtyeight.thirtyeight.presentation.screens.mechanicsession

/**
 * Created by nikolozakhvlediani on 4/9/21.
 */
sealed class MechanicSessionUiAction {

    object CloseClicked : MechanicSessionUiAction()
    class NavigateToResult(val points: Int, val from: Int) : MechanicSessionUiAction()
    object LoadNextClicked : MechanicSessionUiAction()
    object RetryClicked : MechanicSessionUiAction()
}