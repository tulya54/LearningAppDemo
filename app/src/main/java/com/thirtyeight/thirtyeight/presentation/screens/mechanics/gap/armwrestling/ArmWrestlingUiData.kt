package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.armwrestling

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.armwrestling.ArmWrestlingGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.armwrestling.ArmWrestlingGapOptionData
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/10/21.
 */
data class ArmWrestlingUiData(
        val question: GapQuestionEntity<ArmWrestlingGapData, ArmWrestlingGapOptionData>
) : Serializable