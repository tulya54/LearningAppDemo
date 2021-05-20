package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
data class GapViewState<GapData, OptionData>(
        val question: GapQuestionEntity<GapData, OptionData>?,
        val gaps: List<Long>
) : ViewState()