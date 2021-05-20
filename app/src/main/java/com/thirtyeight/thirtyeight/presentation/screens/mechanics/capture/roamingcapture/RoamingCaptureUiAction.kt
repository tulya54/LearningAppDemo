package com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.roamingcapture

import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.RoamingCaptureItemEntity

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
sealed class RoamingCaptureUiAction {

    class AnswerChosen(val roamingCaptureItemEntity: RoamingCaptureItemEntity) :
            RoamingCaptureUiAction()
}