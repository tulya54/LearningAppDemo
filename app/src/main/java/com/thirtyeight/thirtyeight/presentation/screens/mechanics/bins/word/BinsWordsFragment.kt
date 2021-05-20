package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.word

import androidx.navigation.fragment.navArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.BinsFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins.BinsLayout
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins.BinsWordsLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
class BinsWordsFragment : BinsFragment<String>() {

    private val args: BinsWordsFragmentArgs by navArgs()

    override fun createBinLayout(): BinsLayout<*, String> =
            BinsWordsLayout(requireContext())

    override val viewModel by viewModel<BinsWordsViewModel> {
        parametersOf(args.data.data)
    }

    companion object {

        fun createInstance(args: BinsWordsFragmentArgs) =
                BinsWordsFragment().apply {
                    arguments = args.toBundle()
                }
    }
}