package com.thirtyeight.thirtyeight.domain.entities.mechanics.capture

import com.thirtyeight.thirtyeight.domain.valueobjects.RoamingCaptureType
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
data class RoamingCaptureItemEntity(
        val id: Long,
        val image: Int,
        val roamingCaptureType: RoamingCaptureType,
        val removeTime: Long
) : Serializable