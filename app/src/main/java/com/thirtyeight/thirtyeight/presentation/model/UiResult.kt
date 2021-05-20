package com.thirtyeight.thirtyeight.presentation.model

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
sealed class UiResult<out T> {

    data class Success<out T>(val data: T) : UiResult<T>()
    data class Error(val exception: Throwable) : UiResult<Nothing>()
    object Loading : UiResult<Nothing>()
}