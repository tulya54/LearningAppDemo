package com.thirtyeight.thirtyeight.presentation.screens.mechanics.balanceweights

import com.thirtyeight.thirtyeight.domain.entities.mechanics.balanceweight.BalanceWeightItemEntity
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 4/3/21.
 */
data class BalanceWeightsViewState(
        val title: String,
        val leftItem: BalanceWeightItemEntity,
        val rightItem: BalanceWeightItemEntity,
        val initialWeightRight: Int,
        val step: Int,
        val answer: Int
) : ViewState()