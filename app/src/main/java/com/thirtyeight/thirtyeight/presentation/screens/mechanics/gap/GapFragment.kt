package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap

import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.widget.FrameLayout
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.CTextView
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseMechanicFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.GapLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
abstract class GapFragment<GapData, OptionData, VM : GapViewModel<GapData, OptionData>, GL : GapLayout<*, GapData, OptionData>> :
        BaseMechanicFragment<VM>() {

    private val sharedViewModel by sharedViewModel<MechanicSessionViewModel>()

    private lateinit var gapLayout: GL

    abstract fun createGapLayout(): GL

    override fun createMiddleContainerView() = createGapLayout().also {
        gapLayout = it
        it.optionChosen = { id, index ->
            viewModel.processUiAction(GapUiAction.OptionChosen(id, index))
        }
        it.gapClicked = {
            viewModel.processUiAction(GapUiAction.GapClicked(it))
        }
    }

    override fun createBottomContainerView() =
            FrameLayout(requireContext()).apply {
                val contextWrapper = ContextThemeWrapper(context, R.style.SentenceGapButtonReady)
                //  Add button
                addView(
                        CTextView(contextWrapper, null, R.style.SentenceGapButtonReady).apply {
                            setText(R.string.ready)
                            setOnClickListener {
                                viewModel.processUiAction(GapUiAction.CheckClicked)
                            }
                        },
                        FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 45.asPx)
                )
                val padding = resources.getDimension(R.dimen.mech_page_padding).toInt()
                setPadding(padding, padding, padding, padding)
            }

    override fun onViewModelCreated(viewModel: VM) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                question?.let { question ->
                    gapLayout.setQuestion(question)
                }
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