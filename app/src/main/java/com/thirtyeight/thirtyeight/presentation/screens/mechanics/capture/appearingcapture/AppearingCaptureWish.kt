package com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.appearingcapture

import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.AppearingCaptureItemEntity

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
sealed class AppearingCaptureWish {

    class DataLoaded(val list: List<AppearingCaptureItemEntity>) : AppearingCaptureWish()
    class UpdateTime(val timeLeft: Long) : AppearingCaptureWish()
    object IncrementPoint : AppearingCaptureWish()
    object IncrementTries : AppearingCaptureWish()
}