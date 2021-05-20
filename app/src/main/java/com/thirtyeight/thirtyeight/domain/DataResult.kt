package com.thirtyeight.thirtyeight.domain

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
sealed class DataResult<out T> {

    class Success<out T>(val data: T) : DataResult<T>()
    class Error(val throwable: Throwable) : DataResult<Nothing>()
}