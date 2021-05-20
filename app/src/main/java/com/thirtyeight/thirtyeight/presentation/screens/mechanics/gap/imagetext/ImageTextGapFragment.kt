package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imagetext

import androidx.navigation.fragment.navArgs
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imagetext.ImageTextGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imagetext.ImageTextGapOptionData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.GapFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.ImageTextGapLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
class ImageTextGapFragment :
        GapFragment<ImageTextGapData, ImageTextGapOptionData, ImageTextGapViewModel, ImageTextGapLayout>() {

    private val args: ImageTextGapFragmentArgs by navArgs()

    override val viewModel by viewModel<ImageTextGapViewModel> {
        parametersOf(args.data.question)
    }

    override fun createGapLayout() = ImageTextGapLayout(requireContext())

    companion object {

        fun createInstance(args: ImageTextGapFragmentArgs) =
                ImageTextGapFragment().apply {
                    arguments = args.toBundle()
                }
    }
}