package com.thirtyeight.thirtyeight.domain.usecase.mechanics.balanceweight

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.balanceweight.BalanceWeightItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.balanceweight.BalanceWeightQuestionEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase

/**
 * Created by nikolozakhvlediani on 4/3/21.
 */
class GetBalanceWeightQuestionUseCase : BaseUseCase<Unit, BalanceWeightQuestionEntity>() {

    override fun execute(input: Unit): BalanceWeightQuestionEntity {
        return BalanceWeightQuestionEntity(
                R.string.balance_objects_correctly,
                "How many centimeters in one meter?",
                BalanceWeightItemEntity(1, "m", R.drawable.frog),
                BalanceWeightItemEntity(10, "cm", R.drawable.dog),
                10,
                100
        )
    }
}