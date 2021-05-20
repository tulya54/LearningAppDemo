package com.thirtyeight.thirtyeight.presentation.di

import com.thirtyeight.thirtyeight.domain.entities.mechanics.MechanicDataEntity
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
import com.thirtyeight.thirtyeight.presentation.screens.auth.AuthViewModel
import com.thirtyeight.thirtyeight.presentation.screens.auth.forgotpassword.ForgotPasswordViewModel
import com.thirtyeight.thirtyeight.presentation.screens.auth.login.LoginViewModel
import com.thirtyeight.thirtyeight.presentation.screens.auth.register.getstarted.GetStartedViewModel
import com.thirtyeight.thirtyeight.presentation.screens.auth.register.laststep.RegisterLastStepViewModel
import com.thirtyeight.thirtyeight.presentation.screens.auth.register.stepone.RegisterStepOneViewModel
import com.thirtyeight.thirtyeight.presentation.screens.auth.register.steptwo.RegisterStepTwoViewModel
import com.thirtyeight.thirtyeight.presentation.screens.comingsoon.ComingSoonViewModel
import com.thirtyeight.thirtyeight.presentation.screens.main.discover.DiscoverViewModel
import com.thirtyeight.thirtyeight.presentation.screens.main.mytopics.MyTopicsViewModel
import com.thirtyeight.thirtyeight.presentation.screens.main.profile.changepassword.ChangePasswordViewModel
import com.thirtyeight.thirtyeight.presentation.screens.main.profile.editprofile.EditProfileViewModel
import com.thirtyeight.thirtyeight.presentation.screens.main.profile.settings.SettingsViewModel
import com.thirtyeight.thirtyeight.presentation.screens.lesson.LessonViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.MechanicsViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.balanceweights.BalanceWeightsViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.image.BinsImagesViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.word.BinsWordsViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.captcha.CaptchaViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.appearingcapture.AppearingCaptureViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.roamingcapture.RoamingCaptureViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.columns.ColumnsViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.armwrestling.ArmWrestlingViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imageimage.ImageImageGapViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imagetext.ImageTextGapViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.image.ImageOrderGapViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.word.WordOrderGapViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.sentence.SentenceGapViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.result.ResultViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.tinder.TinderViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.trivia.TriviaViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.trueorfalse.TrueOrFalseViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.wordsearch.WordSearchViewModel
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.DefaultCoroutineContextProvider
import com.thirtyeight.thirtyeight.util.DefaultStringResourceMapper
import com.thirtyeight.thirtyeight.util.StringResourceMapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */

val viewModelModule = module {
    // Auth
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ForgotPasswordViewModel(get(), get()) }
    viewModel { GetStartedViewModel(get(), get()) }
    viewModel { RegisterStepOneViewModel(get(), get()) }
    viewModel { RegisterStepTwoViewModel(get(), get()) }
    viewModel { RegisterLastStepViewModel(get(), get(), get()) }
    viewModel { AuthViewModel(get(), get()) }

    // Main
    viewModel { DiscoverViewModel(get(), get()) }
    viewModel { MyTopicsViewModel(get(), get()) }
    viewModel { LessonViewModel(get(), get()) }

    // Profile
    viewModel { EditProfileViewModel(get(), get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { ChangePasswordViewModel(get(), get()) }

    // Mechanics
    viewModel { MechanicsViewModel(get(), get()) }
    viewModel { (data: List<MechanicDataEntity>) ->
        MechanicSessionViewModel(data, get(), get())
    }
    viewModel { ComingSoonViewModel(get(), get()) }
    viewModel { ResultViewModel(get(), get()) }
    viewModel { (entity: CaptchaQuestionEntity) ->
        CaptchaViewModel(entity, get(), get(), get(), get())
    }
    viewModel { (items: List<RoamingCaptureItemEntity>) ->
        RoamingCaptureViewModel(items, get(), get(), get(), get(), get())
    }
    viewModel { (items: List<AppearingCaptureItemEntity>) ->
        AppearingCaptureViewModel(items, get(), get(), get(), get(), get())
    }
    viewModel { (items: List<TinderItemEntity>) ->
        TinderViewModel(items, get(), get(), get(), get())
    }
    viewModel { (triviaQuestionEntity: TriviaQuestionEntity) ->
        TriviaViewModel(triviaQuestionEntity, get(), get(), get(), get())
    }
    viewModel { (entity: GapQuestionEntity<SentenceGapData, SentenceGapOptionData>) ->
        SentenceGapViewModel(entity, get(), get(), get(), get())
    }
    viewModel { (entity: GapQuestionEntity<ImageTextGapData, ImageTextGapOptionData>) ->
        ImageTextGapViewModel(entity, get(), get(), get(), get())
    }
    viewModel { (entity: GapQuestionEntity<ImageImageGapData, ImageImageGapOptionData>) ->
        ImageImageGapViewModel(entity, get(), get(), get(), get())
    }
    viewModel { (binsDataEntity: BinsDataEntity<Int>) ->
        BinsImagesViewModel(binsDataEntity, get(), get(), get())
    }
    viewModel { (binsDataEntity: BinsDataEntity<String>) ->
        BinsWordsViewModel(binsDataEntity, get(), get(), get())
    }
    viewModel { (trueOrFalseItemEntity: TriviaQuestionEntity) ->
        TrueOrFalseViewModel(trueOrFalseItemEntity, get(), get(), get(), get())
    }
    viewModel { (entity: GapQuestionEntity<OrderGapData, ImageOrderGapOptionData>) ->
        ImageOrderGapViewModel(entity, get(), get(), get(), get())
    }
    viewModel { (entity: GapQuestionEntity<OrderGapData, WordOrderGapOptionData>) ->
        WordOrderGapViewModel(entity, get(), get(), get(), get())
    }
    viewModel { (entity: GapQuestionEntity<ArmWrestlingGapData, ArmWrestlingGapOptionData>) ->
        ArmWrestlingViewModel(entity, get(), get(), get(), get())
    }
    viewModel { (entity: ColumnsQuestionEntity) ->
        ColumnsViewModel(entity, get(), get(), get(), get())
    }
    viewModel { (entity: BalanceWeightQuestionEntity) ->
        BalanceWeightsViewModel(entity, get(), get(), get())
    }
    viewModel { (entity: WordSearchDataEntity) ->
        WordSearchViewModel(entity, get(), get(), get())
    }

    single<CoroutineContextProvider> { DefaultCoroutineContextProvider() }
    single<StringResourceMapper> { DefaultStringResourceMapper(get()) }
}