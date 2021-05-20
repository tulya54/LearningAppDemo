package com.thirtyeight.thirtyeight.domain.entities.mechanics.bins

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
data class BinFallingItemEntity<BinData>(
        val id: Long,
        val categoryId: Long,
        val data: BinData
) : Serializable