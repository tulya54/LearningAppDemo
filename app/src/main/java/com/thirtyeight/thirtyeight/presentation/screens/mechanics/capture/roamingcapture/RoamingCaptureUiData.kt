package com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.roamingcapture

import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.RoamingCaptureItemEntity
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/10/21.
 */
data class RoamingCaptureUiData(
        val items: List<RoamingCaptureItemEntity>
) : Serializable