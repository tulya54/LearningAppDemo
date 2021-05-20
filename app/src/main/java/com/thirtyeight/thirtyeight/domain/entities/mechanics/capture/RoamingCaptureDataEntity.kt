package com.thirtyeight.thirtyeight.domain.entities.mechanics.capture

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/10/21.
 */
data class RoamingCaptureDataEntity(
        val titleRes: Int,
        val items: List<RoamingCaptureItemEntity>
) : Serializable