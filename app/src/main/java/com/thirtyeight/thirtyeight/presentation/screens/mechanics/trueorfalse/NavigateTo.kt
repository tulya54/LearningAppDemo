package com.thirtyeight.thirtyeight.presentation.screens.mechanics.trueorfalse

/**
 * Created by nikolozakhvlediani on 4/1/21.
 */
sealed class NavigateTo {

    data class Result(val points: Int, val from: Int) : NavigateTo()
}