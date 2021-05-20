package com.thirtyeight.thirtyeight.domain.entities.mechanics.bins

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
data class BinCategoryEntity(
        val id: Long,
        val text: String
) : Serializable