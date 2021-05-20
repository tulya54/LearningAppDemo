package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imagetext

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imagetext.ImageTextGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imagetext.ImageTextGapOptionData
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.CheckGapAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.GetImageTextGapQuestionUseCase
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.GapViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
class ImageTextGapViewModel(
        gapQuestionEntity: GapQuestionEntity<ImageTextGapData, ImageTextGapOptionData>,
        checkGapAnswersUseCase: CheckGapAnswersUseCase,
        private val getImageTextGapQuestionUseCase: GetImageTextGapQuestionUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : GapViewModel<ImageTextGapData, ImageTextGapOptionData>(
        gapQuestionEntity,
        checkGapAnswersUseCase,
        coroutineContextProvider,
        stringResourceMapper
) {

    override fun getGapCount(gapData: ImageTextGapData): Int =
            gapData.gapCount
}