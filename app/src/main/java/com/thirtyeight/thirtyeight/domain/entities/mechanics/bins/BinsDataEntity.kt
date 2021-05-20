package com.thirtyeight.thirtyeight.domain.entities.mechanics.bins

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
data class BinsDataEntity<BinData>(
        val titleRes: Int,
        val bins: List<BinCategoryEntity>,
        val fallingItems: List<BinFallingItemEntity<BinData>>
) : Serializable