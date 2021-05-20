package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.word

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.OrderGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.WordOrderGapOptionData
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/10/21.
 */
data class WordOrderGapUiData(
        val question: GapQuestionEntity<OrderGapData, WordOrderGapOptionData>
) : Serializable