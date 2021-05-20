package com.thirtyeight.thirtyeight.domain.usecase.mechanics.tinder

import com.thirtyeight.thirtyeight.domain.entities.mechanics.tinder.TinderItemEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.domain.valueobjects.TinderType

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
class CheckTinderAnswerUseCase : BaseUseCase<CheckTinderAnswerUseCase.Answer, Boolean>() {

    override fun execute(input: Answer): Boolean =
            input.chosenType == input.tinderItem.tinderType

    data class Answer(
            val tinderItem: TinderItemEntity,
            val chosenType: TinderType
    )
}