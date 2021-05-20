package com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
data class SentenceGapData(
        val data: List<SentenceGapItem>
) : Serializable