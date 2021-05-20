package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.image

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.ImageOrderGapOptionData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.OrderGapData
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.CheckGapAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.GetImageOrderGapQuestionUseCase
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.OrderGapViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ImageOrderGapViewModel(
        gapQuestionEntity: GapQuestionEntity<OrderGapData, ImageOrderGapOptionData>,
        private val getImageOrderGapQuestionUseCase: GetImageOrderGapQuestionUseCase,
        checkGapAnswersUseCase: CheckGapAnswersUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : OrderGapViewModel<ImageOrderGapOptionData>(gapQuestionEntity, checkGapAnswersUseCase, coroutineContextProvider, stringResourceMapper)