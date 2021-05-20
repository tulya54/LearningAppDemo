package com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapOptionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.armwrestling.ArmWrestlingGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.armwrestling.ArmWrestlingGapOptionData
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class GetArmWrestlingGapQuestionUseCase :
        BaseUseCase<Unit, GapQuestionEntity<ArmWrestlingGapData, ArmWrestlingGapOptionData>>() {

    override fun execute(input: Unit): GapQuestionEntity<ArmWrestlingGapData, ArmWrestlingGapOptionData> {
        val greater = GenerateId()
        return GapQuestionEntity(
                R.string.put_correct_comparision_sign,
                ArmWrestlingGapData("25", "16"),
                listOf(
                        GapOptionEntity(greater, ArmWrestlingGapOptionData(">")),
                        GapOptionEntity(GenerateId(), ArmWrestlingGapOptionData("=")),
                        GapOptionEntity(GenerateId(), ArmWrestlingGapOptionData("<"))
                ),
                listOf(greater)
        )
    }
}