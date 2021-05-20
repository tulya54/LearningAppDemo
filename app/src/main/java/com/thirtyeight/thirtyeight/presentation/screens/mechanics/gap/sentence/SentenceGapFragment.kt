package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.sentence

import androidx.navigation.fragment.navArgs
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapOptionData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.GapFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.SentenceGapLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
class SentenceGapFragment :
        GapFragment<SentenceGapData, SentenceGapOptionData, SentenceGapViewModel, SentenceGapLayout>() {

    private val args: SentenceGapFragmentArgs by navArgs()

    override val viewModel by viewModel<SentenceGapViewModel> {
        parametersOf(args.data.question)
    }

    override fun createGapLayout() = SentenceGapLayout(requireContext())

    companion object {

        fun createInstance(args: SentenceGapFragmentArgs) =
                SentenceGapFragment().apply {
                    arguments = args.toBundle()
                }
    }
}