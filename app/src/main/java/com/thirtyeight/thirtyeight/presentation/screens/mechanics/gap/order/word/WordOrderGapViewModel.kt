package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.word

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.OrderGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.WordOrderGapOptionData
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.CheckGapAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.GetWordOrderGapQuestionUseCase
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.OrderGapViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class WordOrderGapViewModel(
        gapQuestionEntity: GapQuestionEntity<OrderGapData, WordOrderGapOptionData>,
        private val getWordOrderGapQuestionUseCase: GetWordOrderGapQuestionUseCase,
        checkGapAnswersUseCase: CheckGapAnswersUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : OrderGapViewModel<WordOrderGapOptionData>(gapQuestionEntity, checkGapAnswersUseCase, coroutineContextProvider, stringResourceMapper)