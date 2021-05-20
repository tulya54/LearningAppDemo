package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins

sealed class NavigateTo {

    data class Result(val points: Int, val from: Int) : NavigateTo()
}