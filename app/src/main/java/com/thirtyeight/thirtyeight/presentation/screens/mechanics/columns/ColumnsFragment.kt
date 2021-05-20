package com.thirtyeight.thirtyeight.presentation.screens.mechanics.columns

import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.fragment.navArgs
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.CTextView
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseMechanicFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.columns.ColumnsLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ColumnsFragment : BaseMechanicFragment<ColumnsViewModel>() {

    private val args: ColumnsFragmentArgs by navArgs()

    private lateinit var columnsLayout: ColumnsLayout

    override val viewModel by viewModel<ColumnsViewModel> {
        parametersOf(args.data)
    }

    private val sharedViewModel by sharedViewModel<MechanicSessionViewModel>()

    override fun createBottomContainerView() =
            FrameLayout(requireContext()).apply {
                val contextWrapper =
                        ContextThemeWrapper(context, R.style.MechPrimaryButtonStyle)
                addView(
                        CTextView(contextWrapper, null, R.style.MechPrimaryButtonStyle).apply {
                            setText(R.string.check)
                            setOnClickListener {
                                viewModel.processUiAction(ColumnsUiActions.CheckClicked)
                            }
                        },
                        FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 45.asPx).apply {
                        }
                )
                val padding = resources.getDimension(R.dimen.mech_page_padding).toInt()
                setPadding(padding, padding, padding, padding)
            }

    override fun createMiddleContainerView() =
            ColumnsLayout(requireContext()).also {
                columnsLayout = it
            }

    override fun initViews(view: View) {
        super.initViews(view)
        columnsLayout.onItemClick = {
            viewModel.processUiAction(ColumnsUiActions.OptionChosen(it))
        }
    }

    override fun onViewModelCreated(viewModel: ColumnsViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                columnsLayout.setData(
                        it.columnOne,
                        it.columnTwo,
                        it.selectedPairs,
                        it.selectedItem
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

        fun createInstance(args: ColumnsFragmentArgs) =
                ColumnsFragment().apply {
                    arguments = args.toBundle()
                }
    }
}