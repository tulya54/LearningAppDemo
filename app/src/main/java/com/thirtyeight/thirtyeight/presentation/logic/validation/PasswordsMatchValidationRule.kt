package com.thirtyeight.thirtyeight.presentation.logic.validation

/**
 * Created by nikolozakhvlediani on 4/18/21.
 */
class PasswordsMatchValidationRule : ValidationRule<Pair<String, String>> {

    override fun validate(value: Pair<String, String>) =
            value.first == value.second
}