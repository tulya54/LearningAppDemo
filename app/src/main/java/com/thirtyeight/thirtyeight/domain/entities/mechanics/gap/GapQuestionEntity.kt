package com.thirtyeight.thirtyeight.domain.entities.mechanics.gap

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
data class GapQuestionEntity<GapData, OptionData>(val titleRes: Int, val gapData: GapData,
        val options: List<GapOptionEntity<OptionData>>, val answers: List<Long>): Serializable