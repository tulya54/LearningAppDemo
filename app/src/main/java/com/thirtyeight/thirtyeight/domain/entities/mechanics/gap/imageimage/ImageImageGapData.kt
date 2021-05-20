package com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
data class ImageImageGapData(
        val image: Int,
        val parts: Int,
        val gapIndexes: List<Int>
) : Serializable