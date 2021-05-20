package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
sealed class NavigateTo {

    data class Result(val points: Int, val from: Int) : NavigateTo()
}