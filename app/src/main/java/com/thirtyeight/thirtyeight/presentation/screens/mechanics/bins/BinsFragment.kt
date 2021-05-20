package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins

import android.os.Bundle
import android.view.View
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseMechanicFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins.BinsLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
abstract class BinsFragment<BinData> : BaseMechanicFragment<BinsViewModel<BinData>>() {

    private val sharedViewModel by sharedViewModel<MechanicSessionViewModel>()

    private lateinit var binsLayout: BinsLayout<*, BinData>
    private lateinit var leftButton: View
    private lateinit var rightButton: View

    abstract fun createBinLayout(): BinsLayout<*, BinData>

    override fun createMiddleContainerView(): View {
        return createBinLayout().also {
            binsLayout = it
            it.categoryClicked = { index ->
                viewModel.processUiAction(BinsUiAction.CategoryClicked(index))
            }
            it.itemReachedBottom = {
                viewModel.processUiAction(BinsUiAction.ItemReachedBottom)
            }
        }
    }

    override fun createBottomContainerView(): View? {
        val view = context.inflateLayout(R.layout.layout_bins_buttons)
        leftButton = view.findViewById(R.id.left_button)
        rightButton = view.findViewById(R.id.right_button)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        leftButton.setOnClickListener {
            viewModel.processUiAction(BinsUiAction.LeftArrowClicked)
        }
        rightButton.setOnClickListener {
            viewModel.processUiAction(BinsUiAction.RightArrowClicked)
        }
    }

    override fun onViewModelCreated(viewModel: BinsViewModel<BinData>) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                binsLayout.setData(it.bins, it.currentFallingItem, it.selectedBinIndex)
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
}