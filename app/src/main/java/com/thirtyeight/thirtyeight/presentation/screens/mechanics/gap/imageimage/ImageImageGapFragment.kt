package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.imageimage

import androidx.navigation.fragment.navArgs
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage.ImageImageGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.imageimage.ImageImageGapOptionData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.GapFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.ImageImageGapLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
class ImageImageGapFragment :
        GapFragment<ImageImageGapData, ImageImageGapOptionData, ImageImageGapViewModel, ImageImageGapLayout>() {

    private val args: ImageImageGapFragmentArgs by navArgs()

    override val viewModel by viewModel<ImageImageGapViewModel> {
        parametersOf(args.data.question)
    }

    override fun createGapLayout() = ImageImageGapLayout(requireContext())

    companion object {

        fun createInstance(args: ImageImageGapFragmentArgs) =
                ImageImageGapFragment().apply {
                    arguments = args.toBundle()
                }
    }
}