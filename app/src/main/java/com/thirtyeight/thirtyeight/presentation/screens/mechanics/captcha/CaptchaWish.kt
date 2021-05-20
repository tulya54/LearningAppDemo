package com.thirtyeight.thirtyeight.presentation.screens.mechanics.captcha

import com.thirtyeight.thirtyeight.domain.entities.mechanics.captcha.CaptchaQuestionEntity

/**
 * Created by nikolozakhvlediani on 3/31/21.
 */
sealed class CaptchaWish {

    class DataLoaded(val captchaQuestionEntity: CaptchaQuestionEntity) : CaptchaWish()
    class SelectItem(val itemId: Long) : CaptchaWish()
    class DeselectItem(val itemId: Long) : CaptchaWish()
}