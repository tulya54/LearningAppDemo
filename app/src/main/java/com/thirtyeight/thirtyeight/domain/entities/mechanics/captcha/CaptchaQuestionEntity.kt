package com.thirtyeight.thirtyeight.domain.entities.mechanics.captcha

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/31/21.
 */
data class CaptchaQuestionEntity(
        val titleRes: Int,
        val text: String,
        val options: List<CaptchaOptionEntity>,
        val rightAnswers: List<Long>
) : Serializable