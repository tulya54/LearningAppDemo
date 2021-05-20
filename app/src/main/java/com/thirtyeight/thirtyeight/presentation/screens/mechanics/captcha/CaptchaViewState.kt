package com.thirtyeight.thirtyeight.presentation.screens.mechanics.captcha

import com.thirtyeight.thirtyeight.domain.entities.mechanics.captcha.CaptchaOptionEntity
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 3/31/21.
 */
data class CaptchaViewState(
        val questionText: String,
        val data: List<CaptchaOptionEntity>,
        val selectedOptions: List<Long>,
        val rightAnswers: List<Long>
) : ViewState()