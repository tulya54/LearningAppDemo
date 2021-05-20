package com.thirtyeight.thirtyeight.presentation.logic.validation

/**
 * Created by nikolozakhvlediani on 4/17/21.
 */
interface ValidationRule<T> {

    fun validate(value: T): Boolean
}