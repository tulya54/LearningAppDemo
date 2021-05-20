package com.thirtyeight.thirtyeight.presentation.logic.validation

/**
 * Created by nikolozakhvlediani on 4/17/21.
 */
class EmailFormatValidationRule : ValidationRule<String> {

    override fun validate(value: String): Boolean =
            EMAIL_REGEX.toRegex().matches(value)

    companion object {

        private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
    }
}