package com.thirtyeight.thirtyeight.domain.entities.mechanics.balanceweight

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/3/21.
 */
data class BalanceWeightQuestionEntity(
        val titleRes: Int,
        val text: String,
        val itemOne: BalanceWeightItemEntity,
        val itemTwo: BalanceWeightItemEntity,
        val increaseCount: Int,
        val answer: Int
) : Serializable