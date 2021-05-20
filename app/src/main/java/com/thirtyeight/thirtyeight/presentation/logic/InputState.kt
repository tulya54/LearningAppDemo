package com.thirtyeight.thirtyeight.presentation.logic

import androidx.annotation.StringRes

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
data class InputState(
        val id: Long,
        val text: String,
        val enabled: Boolean = true,
        val showError: Boolean = false,
        @StringRes val errorRes: Int = 0
)