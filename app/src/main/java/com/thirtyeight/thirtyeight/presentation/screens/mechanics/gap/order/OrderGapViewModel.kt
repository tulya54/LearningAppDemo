package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.OrderGapData
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.CheckGapAnswersUseCase
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.GapViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
abstract class OrderGapViewModel<GapOptionData>(
        gapQuestionEntity: GapQuestionEntity<OrderGapData, GapOptionData>,
        checkGapAnswersUseCase: CheckGapAnswersUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : GapViewModel<OrderGapData, GapOptionData>(gapQuestionEntity, checkGapAnswersUseCase, coroutineContextProvider, stringResourceMapper) {

    override fun getGapCount(gapData: OrderGapData): Int =
            gapData.gapCount
}