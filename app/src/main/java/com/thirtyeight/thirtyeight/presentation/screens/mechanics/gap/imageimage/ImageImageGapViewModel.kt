package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imageimage

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage.ImageImageGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage.ImageImageGapOptionData
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.CheckGapAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.GetImageImageGapQuestionUseCase
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.GapViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 3/28/21.
 */
class ImageImageGapViewModel(
        gapQuestionEntity: GapQuestionEntity<ImageImageGapData, ImageImageGapOptionData>,
        checkGapAnswersUseCase: CheckGapAnswersUseCase,
        private val getImageImageGapQuestionUseCase: GetImageImageGapQuestionUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : GapViewModel<ImageImageGapData, ImageImageGapOptionData>(
        gapQuestionEntity,
        checkGapAnswersUseCase,
        coroutineContextProvider,
        stringResourceMapper
) {

    override fun getGapCount(gapData: ImageImageGapData): Int =
            gapData.gapIndexes.size
}