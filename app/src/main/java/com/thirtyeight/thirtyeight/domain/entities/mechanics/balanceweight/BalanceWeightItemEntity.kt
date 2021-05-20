package com.thirtyeight.thirtyeight.domain.entities.mechanics.balanceweight

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/3/21.
 */
data class BalanceWeightItemEntity(
        val weight: Int,
        val measureUnit: String,
        val imageRes: Int
) : Serializable