package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.armwrestling

import androidx.navigation.fragment.navArgs
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.armwrestling.ArmWrestlingGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.armwrestling.ArmWrestlingGapOptionData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.GapFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.ArmWrestlingGapLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ArmWrestlingFragment :
        GapFragment<ArmWrestlingGapData, ArmWrestlingGapOptionData, ArmWrestlingViewModel, ArmWrestlingGapLayout>() {

    private val args: ArmWrestlingFragmentArgs by navArgs()

    override val viewModel by viewModel<ArmWrestlingViewModel> {
        parametersOf(args.data.question)
    }

    override fun createGapLayout(): ArmWrestlingGapLayout =
            ArmWrestlingGapLayout(requireContext())

    companion object {

        fun createInstance(args: ArmWrestlingFragmentArgs) =
                ArmWrestlingFragment().apply {
                    arguments = args.toBundle()
                }
    }
}