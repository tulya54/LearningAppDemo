package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.image

import androidx.navigation.fragment.navArgs
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.ImageOrderGapOptionData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order.OrderGapFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.order.ImageOrderGapLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ImageOrderGapFragment :
        OrderGapFragment<ImageOrderGapOptionData, ImageOrderGapViewModel, ImageOrderGapLayout>() {

    private val args: ImageOrderGapFragmentArgs by navArgs()

    override val viewModel by viewModel<ImageOrderGapViewModel> {
        parametersOf(args.data.question)
    }

    override fun createGapLayout(): ImageOrderGapLayout =
            ImageOrderGapLayout(requireContext())

    companion object {

        fun createInstance(args: ImageOrderGapFragmentArgs) =
                ImageOrderGapFragment().apply {
                    arguments = args.toBundle()
                }
    }
}