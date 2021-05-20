package com.thirtyeight.thirtyeight.domain.usecase.mechanics.roamingcapture

import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.RoamingCaptureItemEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.domain.valueobjects.RoamingCaptureType

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
class CheckRoamingCaptureAnswerUseCase : BaseUseCase<CheckRoamingCaptureAnswerUseCase.Answer, Boolean>() {

    override fun execute(input: Answer): Boolean =
            input.chosenCaptureType == input.roamingCaptureItem.roamingCaptureType

    data class Answer(
            val roamingCaptureItem: RoamingCaptureItemEntity,
            val chosenCaptureType: RoamingCaptureType
    )
}