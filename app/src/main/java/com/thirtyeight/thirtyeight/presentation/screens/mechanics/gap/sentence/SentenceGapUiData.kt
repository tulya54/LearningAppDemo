package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.sentence

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapOptionData
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/10/21.
 */
data class SentenceGapUiData(val question: GapQuestionEntity<SentenceGapData, SentenceGapOptionData>):
        Serializable