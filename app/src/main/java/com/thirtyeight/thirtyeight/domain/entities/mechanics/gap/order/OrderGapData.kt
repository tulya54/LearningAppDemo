package com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
data class OrderGapData(
        val text: String,
        val gapCount: Int
) : Serializable