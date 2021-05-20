package com.thirtyeight.thirtyeight.presentation.screens.mechanics.trivia

/**
 * Created by nikolozakhvlediani on 3/14/21.
 */
sealed class NavigateTo {

    data class Result(val points: Int, val from: Int) : NavigateTo()
}