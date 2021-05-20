package com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.appearingcapture

import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.AppearingCaptureItemEntity

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
sealed class AppearingCaptureUiAction {

    class AnswerChosen(val appearingCaptureItemEntity: AppearingCaptureItemEntity) : AppearingCaptureUiAction()
}