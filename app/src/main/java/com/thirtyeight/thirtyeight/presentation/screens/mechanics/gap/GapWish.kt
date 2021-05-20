package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
sealed class GapWish<out GapData, out OptionData> {

    class DataLoaded<GapData, OptionData>(val question: GapQuestionEntity<GapData, OptionData>) :
            GapWish<GapData, OptionData>()

    class ChooseOption(val optionId: Long, val gapIndex: Int) : GapWish<Nothing, Nothing>()
    class ClearGap(val index: Int) : GapWish<Nothing, Nothing>()
}