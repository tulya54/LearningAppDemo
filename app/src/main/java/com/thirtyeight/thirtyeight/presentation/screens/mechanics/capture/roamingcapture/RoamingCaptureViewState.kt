package com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.roamingcapture

import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.RoamingCaptureItemEntity
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
data class RoamingCaptureViewState(
        val list: List<RoamingCaptureItemEntity>,
        val points: Int,
        val tries: Int,
        val timeLeft: Long
) : ViewState()