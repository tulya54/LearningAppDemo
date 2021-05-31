package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.image

import androidx.navigation.fragment.navArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.BinsFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins.BinsImagesLayout
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins.BinsLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
class BinsImagesFragment: BinsFragment<Int>() {

    private val args: BinsImagesFragmentArgs by navArgs()

    override fun createBinLayout(): BinsLayout<*, Int> =
            BinsImagesLayout(requireContext())

    override val viewModel by viewModel<BinsImagesViewModel> {
        parametersOf(args.data.data)
    }

    companion object {

        fun createInstance(args: BinsImagesFragmentArgs) =
                BinsImagesFragment().apply {
                    arguments = args.toBundle()
                }
    }
}