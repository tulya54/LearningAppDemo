package com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.roamingcapture

import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.RoamingCaptureItemEntity

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
sealed class RoamingCaptureWish {

    class DataLoaded(val list: List<RoamingCaptureItemEntity>) : RoamingCaptureWish()
    class UpdateTime(val time: Long) : RoamingCaptureWish()
    object IncrementPoint : RoamingCaptureWish()
    object IncrementTries : RoamingCaptureWish()
}