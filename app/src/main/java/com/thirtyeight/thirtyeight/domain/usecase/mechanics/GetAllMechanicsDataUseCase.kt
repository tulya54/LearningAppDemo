package com.thirtyeight.thirtyeight.domain.usecase.mechanics

import android.content.Context
import com.thirtyeight.thirtyeight.domain.entities.mechanics.MechanicDataEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.appearingcapture.GetAppearingCaptureDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.balanceweight.GetBalanceWeightQuestionUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.bins.GetImageBinsDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.bins.GetWordBinsDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.captcha.GetCaptchaQuestionUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.columns.GetColumnsQuestionUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.*
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.roamingcapture.GetRoamingCaptureDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.tinder.GetTinderDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.trivia.GetTriviaQuestionUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.trueorfalse.GetTrueOrFalseQuestionUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.wordsearch.GenerateWordSearchMatrixUseCase

/**
 * Created by nikolozakhvlediani on 4/10/21.
 */
class GetAllMechanicsDataUseCase(
        private val context: Context,
        private val getCaptchaQuestionUseCase: GetCaptchaQuestionUseCase,
        private val getRoamingCaptureDataUseCase: GetRoamingCaptureDataUseCase,
        private val getAppearingCaptureDataUseCase: GetAppearingCaptureDataUseCase,
        private val getTriviaQuestionUseCase: GetTriviaQuestionUseCase,
        private val getTinderDataUseCase: GetTinderDataUseCase,
        private val getTrueOrFalseQuestionUseCase: GetTrueOrFalseQuestionUseCase,
        private val getImageBinsDataUseCase: GetImageBinsDataUseCase,
        private val getWordBinsDataUseCase: GetWordBinsDataUseCase,
        private val getImageImageGapQuestionUseCase: GetImageImageGapQuestionUseCase,
        private val getImageTextGapQuestionUseCase: GetImageTextGapQuestionUseCase,
        private val getImageOrderGapQuestionUseCase: GetImageOrderGapQuestionUseCase,
        private val getWordOrderGapQuestionUseCase: GetWordOrderGapQuestionUseCase,
        private val getSentenceGapQuestionUseCase: GetSentenceGapQuestionUseCase,
        private val getArmWrestlingGapQuestionUseCase: GetArmWrestlingGapQuestionUseCase,
        private val getBalanceWeightQuestionUseCase: GetBalanceWeightQuestionUseCase,
        private val getColumnsQuestionUseCase: GetColumnsQuestionUseCase,
        private val generateWordSearchMatrixUseCase: GenerateWordSearchMatrixUseCase
) : BaseUseCase<Unit, List<MechanicDataEntity>>() {

    override fun execute(input: Unit): List<MechanicDataEntity> {
        val list = mutableListOf<MechanicDataEntity>()

        val captchaData = getCaptchaQuestionUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.CaptchaMechanicDataEntity(
                        captchaData,
                        getString(captchaData.titleRes)
                )
        )

        val roamingData = getRoamingCaptureDataUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.RoamingCaptureMechanicDataEntity(
                        roamingData.items,
                        getString(roamingData.titleRes)
                )
        )

        val appearingData = getAppearingCaptureDataUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.AppearingCaptureMechanicDataEntity(
                        appearingData.items,
                        getString(appearingData.titleRes)
                )
        )

        val triviaData = getTriviaQuestionUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.TriviaMechanicDataEntity(
                        triviaData,
                        getString(triviaData.titleRes)
                )
        )

        val tinderData = getTinderDataUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.TinderMechanicDataEntity(
                        tinderData.items,
                        getString(tinderData.titleRes)
                )
        )

        val trueOrFalseData = getTrueOrFalseQuestionUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.TrueOrFalseMechanicDataEntity(
                        trueOrFalseData,
                        getString(trueOrFalseData.titleRes)
                )
        )

        val imageBinsData = getImageBinsDataUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.BinsMechanicDataEntity.BinsImagesMechanicDataEntity(
                        imageBinsData,
                        getString(imageBinsData.titleRes)
                )
        )

        val wordsBinsData = getWordBinsDataUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.BinsMechanicDataEntity.BinsWordsMechanicDataEntity(
                        wordsBinsData,
                        getString(wordsBinsData.titleRes)
                )
        )

        val imageImageGapData = getImageImageGapQuestionUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.GapMechanicDataEntity.GapImageImageMechanicDataEntity(
                        imageImageGapData,
                        getString(imageImageGapData.titleRes)
                )
        )

        val imageTextGapData = getImageTextGapQuestionUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.GapMechanicDataEntity.GapImageTextMechanicDataEntity(
                        imageTextGapData,
                        getString(imageTextGapData.titleRes)
                )
        )

        val imageOrderGapData = getImageOrderGapQuestionUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.GapMechanicDataEntity.GapImageOrderMechanicDataEntity(
                        imageOrderGapData,
                        getString(imageOrderGapData.titleRes)
                )
        )

        val wordOrderGapData = getWordOrderGapQuestionUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.GapMechanicDataEntity.GapWordOrderMechanicDataEntity(
                        wordOrderGapData,
                        getString(wordOrderGapData.titleRes)
                )
        )

        val sentenceGapData = getSentenceGapQuestionUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.GapMechanicDataEntity.GapSentenceMechanicDataEntity(
                        sentenceGapData,
                        getString(sentenceGapData.titleRes)
                )
        )

        val armWrestlingData = getArmWrestlingGapQuestionUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.GapMechanicDataEntity.ArmWrestlingMechanicDataEntity(
                        armWrestlingData,
                        getString(armWrestlingData.titleRes)
                )
        )

        val balanceWeightData = getBalanceWeightQuestionUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.BalanceWeightMechanicDataEntity(
                        balanceWeightData,
                        getString(balanceWeightData.titleRes)
                )
        )

        val columnsData = getColumnsQuestionUseCase.execute(Unit)
        list.add(
                MechanicDataEntity.ColumnsMechanicDataEntity(
                        columnsData,
                        getString(columnsData.titleRes)
                )
        )

        val wordSearchData = generateWordSearchMatrixUseCase.execute(
                GenerateWordSearchMatrixUseCase.Input(8, 8)
        )
        list.add(
                MechanicDataEntity.WordSearchMechanicDataEntity(
                        wordSearchData,
                        getString(wordSearchData.titleRes)
                )
        )

        return list.shuffled()
    }

    private fun getString(stringRes: Int) = context.getString(stringRes)
}