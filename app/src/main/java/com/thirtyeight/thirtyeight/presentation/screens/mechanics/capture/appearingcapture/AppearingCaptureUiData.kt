package com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.appearingcapture

import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.AppearingCaptureItemEntity
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/10/21.
 */
data class AppearingCaptureUiData(
        val items: List<AppearingCaptureItemEntity>
) : Serializable