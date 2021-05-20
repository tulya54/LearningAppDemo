package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.word

import androidx.navigation.fragment.navArgs
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.WordOrderGapOptionData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.OrderGapFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.order.WordOrderGapLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class WordOrderGapFragment :
        OrderGapFragment<WordOrderGapOptionData, WordOrderGapViewModel, WordOrderGapLayout>() {

    private val args: WordOrderGapFragmentArgs by navArgs()

    override val viewModel by viewModel<WordOrderGapViewModel> {
        parametersOf(args.data.question)
    }

    override fun createGapLayout(): WordOrderGapLayout =
            WordOrderGapLayout(requireContext())

    companion object {

        fun createInstance(args: WordOrderGapFragmentArgs) =
                WordOrderGapFragment().apply {
                    arguments = args.toBundle()
                }
    }
}