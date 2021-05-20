package com.thirtyeight.thirtyeight.domain.usecase.mechanics.appearingcapture

import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.AppearingCaptureItemEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.domain.valueobjects.AppearingCaptureType

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
class CheckAppearingCaptureAnswerUseCase :
        BaseUseCase<CheckAppearingCaptureAnswerUseCase.Answer, Boolean>() {

    override fun execute(input: Answer): Boolean =
            input.chosenCaptureType == input.appearingCaptureItemEntity.appearingCaptureType

    data class Answer(
            val appearingCaptureItemEntity: AppearingCaptureItemEntity,
            val chosenCaptureType: AppearingCaptureType
    )
}