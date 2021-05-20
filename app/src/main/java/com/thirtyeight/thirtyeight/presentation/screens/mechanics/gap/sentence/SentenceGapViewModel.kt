package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.sentence

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapItem
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapOptionData
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.CheckGapAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.GetSentenceGapQuestionUseCase
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.GapViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
class SentenceGapViewModel(
        gapQuestionEntity: GapQuestionEntity<SentenceGapData, SentenceGapOptionData>,
        checkGapAnswersUseCase: CheckGapAnswersUseCase,
        private val getSentenceGapQuestionUseCase: GetSentenceGapQuestionUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : GapViewModel<SentenceGapData, SentenceGapOptionData>(gapQuestionEntity, checkGapAnswersUseCase, coroutineContextProvider, stringResourceMapper) {

    override fun getGapCount(gapData: SentenceGapData): Int =
            gapData.data.count { it is SentenceGapItem.Gap }
}