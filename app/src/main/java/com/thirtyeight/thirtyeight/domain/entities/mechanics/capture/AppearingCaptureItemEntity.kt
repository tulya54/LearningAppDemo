package com.thirtyeight.thirtyeight.domain.entities.mechanics.capture

import com.thirtyeight.thirtyeight.domain.valueobjects.AppearingCaptureType
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
data class AppearingCaptureItemEntity(
        val id: Long,
        val image: Int,
        val appearingCaptureType: AppearingCaptureType,
        val removeTime: Long
) : Serializable