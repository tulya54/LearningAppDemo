package com.thirtyeight.thirtyeight.domain.entities.mechanics

import com.thirtyeight.thirtyeight.domain.entities.mechanics.balanceweight.BalanceWeightQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinsDataEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.captcha.CaptchaQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.AppearingCaptureItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.RoamingCaptureItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.columns.ColumnsQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.armwrestling.ArmWrestlingGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.armwrestling.ArmWrestlingGapOptionData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage.ImageImageGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage.ImageImageGapOptionData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imagetext.ImageTextGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imagetext.ImageTextGapOptionData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.ImageOrderGapOptionData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.OrderGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.WordOrderGapOptionData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapOptionData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.tinder.TinderItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaQuestionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.wordsearch.WordSearchDataEntity
import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/9/21.
 */
sealed class MechanicDataEntity(
        open val title: String
) : Serializable {

    data class BalanceWeightMechanicDataEntity(
            val question: BalanceWeightQuestionEntity,
            override val title: String
    ) :
            MechanicDataEntity(title)

    sealed class BinsMechanicDataEntity<T>(
            open val data: BinsDataEntity<T>,
            title: String
    ) :
            MechanicDataEntity(title) {

        data class BinsImagesMechanicDataEntity(
                override val data: BinsDataEntity<Int>,
                override val title: String
        ) :
                BinsMechanicDataEntity<Int>(data, title)

        data class BinsWordsMechanicDataEntity(
                override val data: BinsDataEntity<String>,
                override val title: String
        ) :
                BinsMechanicDataEntity<String>(data, title)
    }

    data class CaptchaMechanicDataEntity(
            val question: CaptchaQuestionEntity,
            override val title: String
    ) : MechanicDataEntity(title)

    data class AppearingCaptureMechanicDataEntity(
            val data: List<AppearingCaptureItemEntity>,
            override val title: String
    ) : MechanicDataEntity(title)

    data class RoamingCaptureMechanicDataEntity(
            val data: List<RoamingCaptureItemEntity>,
            override val title: String
    ) : MechanicDataEntity(title)

    data class ColumnsMechanicDataEntity(
            val question: ColumnsQuestionEntity,
            override val title: String
    ) : MechanicDataEntity(title)

    sealed class GapMechanicDataEntity<QData, OData>(
            open val question: GapQuestionEntity<QData, OData>,
            override val title: String
    ) : MechanicDataEntity(title) {

        data class ArmWrestlingMechanicDataEntity(
                override val question: GapQuestionEntity<ArmWrestlingGapData, ArmWrestlingGapOptionData>,
                override val title: String
        ) : GapMechanicDataEntity<ArmWrestlingGapData, ArmWrestlingGapOptionData>(question, title)

        data class GapImageImageMechanicDataEntity(
                override val question: GapQuestionEntity<ImageImageGapData, ImageImageGapOptionData>,
                override val title: String
        ) : GapMechanicDataEntity<ImageImageGapData, ImageImageGapOptionData>(question, title)

        data class GapImageTextMechanicDataEntity(
                override val question: GapQuestionEntity<ImageTextGapData, ImageTextGapOptionData>,
                override val title: String
        ) : GapMechanicDataEntity<ImageTextGapData, ImageTextGapOptionData>(question, title)

        data class GapImageOrderMechanicDataEntity(
                override val question: GapQuestionEntity<OrderGapData, ImageOrderGapOptionData>,
                override val title: String
        ) : GapMechanicDataEntity<OrderGapData, ImageOrderGapOptionData>(question, title)

        data class GapWordOrderMechanicDataEntity(
                override val question: GapQuestionEntity<OrderGapData, WordOrderGapOptionData>,
                override val title: String
        ) : GapMechanicDataEntity<OrderGapData, WordOrderGapOptionData>(question, title)

        data class GapSentenceMechanicDataEntity(
                override val question: GapQuestionEntity<SentenceGapData, SentenceGapOptionData>,
                override val title: String
        ) : GapMechanicDataEntity<SentenceGapData, SentenceGapOptionData>(question, title)
    }

    data class TinderMechanicDataEntity(
            val data: List<TinderItemEntity>,
            override val title: String
    ) : MechanicDataEntity(title)

    data class TriviaMechanicDataEntity(
            val question: TriviaQuestionEntity,
            override val title: String
    ) : MechanicDataEntity(title)

    data class TrueOrFalseMechanicDataEntity(
            val question: TriviaQuestionEntity,
            override val title: String
    ) : MechanicDataEntity(title)

    data class WordSearchMechanicDataEntity(
            val data: WordSearchDataEntity,
            override val title: String
    ) : MechanicDataEntity(title)
}