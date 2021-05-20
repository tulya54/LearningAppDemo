package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.armwrestling

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.armwrestling.ArmWrestlingGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.armwrestling.ArmWrestlingGapOptionData
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.CheckGapAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.GetArmWrestlingGapQuestionUseCase
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.GapViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ArmWrestlingViewModel(
        gapQuestionEntity: GapQuestionEntity<ArmWrestlingGapData, ArmWrestlingGapOptionData>,
        private val getArmWrestlingGapQuestionUseCase: GetArmWrestlingGapQuestionUseCase,
        checkGapAnswersUseCase: CheckGapAnswersUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : GapViewModel<ArmWrestlingGapData, ArmWrestlingGapOptionData>(
        gapQuestionEntity,
        checkGapAnswersUseCase,
        coroutineContextProvider,
        stringResourceMapper
) {

    override fun getGapCount(gapData: ArmWrestlingGapData) = 1
}