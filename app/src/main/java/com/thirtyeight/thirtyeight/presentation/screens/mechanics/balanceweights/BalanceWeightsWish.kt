package com.thirtyeight.thirtyeight.presentation.screens.mechanics.balanceweights

import com.thirtyeight.thirtyeight.domain.entities.mechanics.balanceweight.BalanceWeightQuestionEntity

/**
 * Created by nikolozakhvlediani on 4/3/21.
 */
sealed class BalanceWeightsWish {

    class DataLoaded(val question: BalanceWeightQuestionEntity) : BalanceWeightsWish()
    class ChangeWeight(val weight: Int) : BalanceWeightsWish()
}