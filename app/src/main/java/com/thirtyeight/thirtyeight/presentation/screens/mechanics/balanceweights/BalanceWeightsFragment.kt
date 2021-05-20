package com.thirtyeight.thirtyeight.presentation.screens.mechanics.balanceweights

import android.view.View
import androidx.navigation.fragment.navArgs
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseMechanicFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.balanceweights.BalanceWeightsLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 4/3/21.
 */
class BalanceWeightsFragment : BaseMechanicFragment<BalanceWeightsViewModel>() {

    private val args: BalanceWeightsFragmentArgs by navArgs()

    private lateinit var balanceWeightsLayout: BalanceWeightsLayout
    private lateinit var minusButton: View
    private lateinit var plusButton: View

    override val viewModel by viewModel<BalanceWeightsViewModel> {
        parametersOf(args.data)
    }

    private val sharedViewModel by sharedViewModel<MechanicSessionViewModel>()

    override fun createBottomContainerView(): View? {
        val view = context.inflateLayout(R.layout.layout_balance_weight_buttons)
        minusButton = view.findViewById(R.id.minus_button)
        plusButton = view.findViewById(R.id.plus_button)
        return view
    }

    override fun createMiddleContainerView() =
            BalanceWeightsLayout(requireContext()).also {
                balanceWeightsLayout = it
            }

    override fun initViews(view: View) {
        super.initViews(view)
        minusButton.setOnClickListener {
            viewModel.processUiAction(BalanceWeightsUiAction.DecreaseClicked)
        }
        plusButton.setOnClickListener {
            viewModel.processUiAction(BalanceWeightsUiAction.IncreaseClicked)
        }
    }

    override fun onViewModelCreated(viewModel: BalanceWeightsViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                balanceWeightsLayout.setData(
                        itemOne = BalanceWeightsLayout.WeightItemModel(
                                answer,
                                "${leftItem.weight}${leftItem.measureUnit}",
                                leftItem.imageRes
                        ),
                        itemTwo = BalanceWeightsLayout.WeightItemModel(
                                rightItem.weight,
                                "${rightItem.weight}${rightItem.measureUnit}",
                                rightItem.imageRes
                        ),
                        initialWeightRight = initialWeightRight
                )
            }
        }
        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NavigateTo.Result -> {
                    sharedViewModel.processUiAction(
                            MechanicSessionUiAction.NavigateToResult(
                                    it.points,
                                    it.from
                            )
                    )
                }
            }
        }
    }

    companion object {

        fun createInstance(args: BalanceWeightsFragmentArgs) =
                BalanceWeightsFragment().apply {
                    arguments = args.toBundle()
                }
    }
}