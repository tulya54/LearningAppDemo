package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.captcha

import com.thirtyeight.thirtyeight.domain.entities.mechanics.captcha.CaptchaOptionEntity

/**
 * Created by nikolozakhvlediani on 4/1/21.
 */
data class CaptchaItemModel(
        val captchaOptionEntity: CaptchaOptionEntity,
        val isSelected: Boolean
)