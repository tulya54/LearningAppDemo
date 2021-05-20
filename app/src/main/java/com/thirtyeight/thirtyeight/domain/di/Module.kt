package com.thirtyeight.thirtyeight.domain.di

import com.thirtyeight.thirtyeight.domain.usecase.auth.register.RegisterUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.GetAllMechanicsDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.GetMechanicDataByTypeUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.appearingcapture.CheckAppearingCaptureAnswerUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.appearingcapture.GetAppearingCaptureDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.appearingcapture.GetNextAppearingCaptureItemUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.balanceweight.GetBalanceWeightQuestionUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.bins.GetImageBinsDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.bins.GetWordBinsDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.captcha.CheckCaptchaAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.captcha.GetCaptchaQuestionUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.columns.CheckColumnsAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.columns.GetColumnsQuestionUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.gap.*
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.roamingcapture.CheckRoamingCaptureAnswerUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.roamingcapture.GetNextRoamingCaptureItemUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.roamingcapture.GetRoamingCaptureDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.tinder.CheckTinderAnswerUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.tinder.GetTinderDataUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.trivia.CheckTriviaAnswersUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.trivia.GetTriviaQuestionUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.trueorfalse.GetTrueOrFalseQuestionUseCase
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.wordsearch.GenerateWordSearchMatrixUseCase
import org.koin.dsl.module

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
val useCaseModule = module {

    //region Auth

    single { RegisterUseCase(get()) }

    //endregion

    // region Mechanics
    single { GetCaptchaQuestionUseCase() }
    single { CheckCaptchaAnswersUseCase() }

    single { GetRoamingCaptureDataUseCase(get()) }
    single { GetNextRoamingCaptureItemUseCase(get()) }
    single { CheckRoamingCaptureAnswerUseCase() }

    single { GetTinderDataUseCase(get()) }
    single { CheckTinderAnswerUseCase() }

    single { GetAppearingCaptureDataUseCase(get()) }
    single { GetNextAppearingCaptureItemUseCase(get()) }
    single { CheckAppearingCaptureAnswerUseCase() }

    single { GetTriviaQuestionUseCase(get()) }
    single { CheckTriviaAnswersUseCase() }

    single { CheckGapAnswersUseCase() }
    single { GetSentenceGapQuestionUseCase() }
    single { GetImageTextGapQuestionUseCase() }
    single { GetImageImageGapQuestionUseCase() }

    single { GetImageBinsDataUseCase() }
    single { GetWordBinsDataUseCase() }

    single { GetTrueOrFalseQuestionUseCase() }

    single { GetImageOrderGapQuestionUseCase() }
    single { GetWordOrderGapQuestionUseCase() }

    single { GetArmWrestlingGapQuestionUseCase() }

    single { GetColumnsQuestionUseCase() }
    single { CheckColumnsAnswersUseCase() }

    single { GetBalanceWeightQuestionUseCase() }

    single { GenerateWordSearchMatrixUseCase() }

    single {
        GetAllMechanicsDataUseCase(
                get(), get(), get(), get(), get(), get(), get(), get(), get(),
                get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }

    single { GetMechanicDataByTypeUseCase(get()) }

    // endregion

}