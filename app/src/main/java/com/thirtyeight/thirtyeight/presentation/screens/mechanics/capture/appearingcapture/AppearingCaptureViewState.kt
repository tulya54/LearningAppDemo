package com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.appearingcapture

import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.AppearingCaptureItemEntity
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
data class AppearingCaptureViewState(
        val list: List<AppearingCaptureItemEntity>,
        val points: Int,
        val tries: Int,
        val timeLeft: Long
) : ViewState()