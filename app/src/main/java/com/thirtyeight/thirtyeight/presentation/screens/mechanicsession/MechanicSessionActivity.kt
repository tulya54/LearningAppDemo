package com.thirtyeight.thirtyeight.presentation.screens.mechanicsession

import android.view.LayoutInflater
import androidx.navigation.navArgs
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.ActivityMechanicSessionBinding
import com.thirtyeight.thirtyeight.domain.entities.mechanics.MechanicDataEntity
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.balanceweights.BalanceWeightsFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.balanceweights.BalanceWeightsFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.image.BinsImagesFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.image.BinsImagesFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.image.BinsImagesUiData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.word.BinsWordsFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.word.BinsWordsFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.word.BinsWordsUiData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.captcha.CaptchaFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.captcha.CaptchaFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.appearingcapture.AppearingCaptureFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.appearingcapture.AppearingCaptureFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.appearingcapture.AppearingCaptureUiData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.roamingcapture.RoamingCaptureFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.roamingcapture.RoamingCaptureFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.roamingcapture.RoamingCaptureUiData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.columns.ColumnsFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.columns.ColumnsFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.armwrestling.ArmWrestlingFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.armwrestling.ArmWrestlingFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.armwrestling.ArmWrestlingUiData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imageimage.ImageImageGapFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imageimage.ImageImageGapFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imageimage.ImageImageGapUiData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imagetext.ImageTextGapFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imagetext.ImageTextGapFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imagetext.ImageTextGapUiData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.image.ImageOrderGapFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.image.ImageOrderGapFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.image.ImageOrderGapUiData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.word.WordOrderGapFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.word.WordOrderGapFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.word.WordOrderGapUiData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.sentence.SentenceGapFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.sentence.SentenceGapFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.sentence.SentenceGapUiData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.result.ResultFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.result.ResultFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.tinder.TinderFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.tinder.TinderFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.tinder.TinderUiData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.trivia.TriviaFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.trivia.TriviaFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.trueorfalse.TrueOrFalseFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.trueorfalse.TrueOrFalseFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.wordsearch.WordSearchFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.wordsearch.WordSearchFragmentArgs
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 4/9/21.
 */
class MechanicSessionActivity :
        ViewStateViewModelActivity<ActivityMechanicSessionBinding, MechanicSessionViewModel>() {

    private val args: MechanicSessionActivityArgs by navArgs()

    override fun createBinding(layoutInflater: LayoutInflater): ActivityMechanicSessionBinding =
            ActivityMechanicSessionBinding.inflate(layoutInflater)

    override val viewModel by viewModel<MechanicSessionViewModel> {
        parametersOf(args.mechanics.mechanics)
    }

    private var previousViewState: MechanicSessionViewState? = null

    override fun initViews() {
        super.initViews()
        binding.mechanicsToolbarLayout.closeClicked = {
            viewModel.processUiAction(MechanicSessionUiAction.CloseClicked)
        }
    }

    override fun onViewModelCreated(viewModel: MechanicSessionViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.navigationLiveData.observe(this) {
            when (it) {
                NavigateTo.Finish -> {
                    finish()
                }
            }
        }
        viewModel.viewStateLiveData.observe(this) {
            with(it) {
                binding.mechanicsToolbarLayout.setTitle(title)
                binding.mechanicsToolbarLayout.updateProgress(progress)
                if (resultShown) {
                    if (previousViewState?.resultShown == false) {
                        replaceFragment(
                                R.id.fragment_container,
                                ResultFragment.createInstance(
                                        ResultFragmentArgs(points, from, showNext)
                                )
                        )
                    }
                } else {
                    if (currentStep != previousViewState?.currentStep ||
                            resultShown != previousViewState?.resultShown
                    ) {
                        currentStep?.let {
                            displayMechanic(it)
                        }
                    }
                }
                previousViewState = this
            }
        }
    }

    private fun displayMechanic(entity: MechanicDataEntity) {
        val fragment = when (entity) {
            is MechanicDataEntity.AppearingCaptureMechanicDataEntity -> {
                AppearingCaptureFragment.createInstance(
                        AppearingCaptureFragmentArgs(
                                AppearingCaptureUiData(entity.data)
                        )
                )
            }
            is MechanicDataEntity.BalanceWeightMechanicDataEntity -> {
                BalanceWeightsFragment.createInstance(BalanceWeightsFragmentArgs(entity.question))
            }
            is MechanicDataEntity.BinsMechanicDataEntity.BinsImagesMechanicDataEntity -> {
                BinsImagesFragment.createInstance(
                        BinsImagesFragmentArgs(BinsImagesUiData(entity.data))
                )
            }
            is MechanicDataEntity.BinsMechanicDataEntity.BinsWordsMechanicDataEntity -> {
                BinsWordsFragment.createInstance(
                        BinsWordsFragmentArgs(BinsWordsUiData(entity.data))
                )
            }
            is MechanicDataEntity.CaptchaMechanicDataEntity -> {
                CaptchaFragment.createInstance(
                        CaptchaFragmentArgs(entity.question)
                )
            }
            is MechanicDataEntity.ColumnsMechanicDataEntity -> {
                ColumnsFragment.createInstance(
                        ColumnsFragmentArgs(entity.question)
                )
            }
            is MechanicDataEntity.GapMechanicDataEntity.ArmWrestlingMechanicDataEntity -> {
                ArmWrestlingFragment.createInstance(
                        ArmWrestlingFragmentArgs(
                                ArmWrestlingUiData(
                                        entity.question
                                )
                        )
                )
            }
            is MechanicDataEntity.GapMechanicDataEntity.GapImageImageMechanicDataEntity -> {
                ImageImageGapFragment.createInstance(
                        ImageImageGapFragmentArgs(
                                ImageImageGapUiData(
                                        entity.question
                                )
                        )
                )
            }
            is MechanicDataEntity.GapMechanicDataEntity.GapImageOrderMechanicDataEntity -> {
                ImageOrderGapFragment.createInstance(
                        ImageOrderGapFragmentArgs(
                                ImageOrderGapUiData(
                                        entity.question
                                )
                        )
                )
            }
            is MechanicDataEntity.GapMechanicDataEntity.GapImageTextMechanicDataEntity -> {
                ImageTextGapFragment.createInstance(
                        ImageTextGapFragmentArgs(
                                ImageTextGapUiData(
                                        entity.question
                                )
                        )
                )
            }
            is MechanicDataEntity.GapMechanicDataEntity.GapSentenceMechanicDataEntity -> {
                SentenceGapFragment.createInstance(
                        SentenceGapFragmentArgs(
                                SentenceGapUiData(
                                        entity.question
                                )
                        )
                )
            }
            is MechanicDataEntity.GapMechanicDataEntity.GapWordOrderMechanicDataEntity -> {
                WordOrderGapFragment.createInstance(
                        WordOrderGapFragmentArgs(
                                WordOrderGapUiData(
                                        entity.question
                                )
                        )
                )
            }
            is MechanicDataEntity.RoamingCaptureMechanicDataEntity -> {
                RoamingCaptureFragment.createInstance(
                        RoamingCaptureFragmentArgs(
                                RoamingCaptureUiData(
                                        entity.data
                                )
                        )
                )
            }
            is MechanicDataEntity.TinderMechanicDataEntity -> {
                TinderFragment.createInstance(TinderFragmentArgs(TinderUiData(entity.data)))
            }
            is MechanicDataEntity.TriviaMechanicDataEntity -> {
                TriviaFragment.createInstance(TriviaFragmentArgs(entity.question))
            }
            is MechanicDataEntity.TrueOrFalseMechanicDataEntity -> {
                TrueOrFalseFragment.createInstance(TrueOrFalseFragmentArgs(entity.question))
            }
            is MechanicDataEntity.WordSearchMechanicDataEntity -> {
                WordSearchFragment.createInstance(WordSearchFragmentArgs(entity.data))
            }
        }
        replaceFragment(R.id.fragment_container, fragment)
    }
}