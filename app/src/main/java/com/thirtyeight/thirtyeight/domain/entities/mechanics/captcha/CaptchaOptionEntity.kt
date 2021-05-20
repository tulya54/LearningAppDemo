package com.thirtyeight.thirtyeight.domain.entities.mechanics.captcha

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/31/21.
 */
data class CaptchaOptionEntity(
        val id: Long,
        val data: Int
) : Serializable