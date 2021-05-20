package com.thirtyeight.thirtyeight.presentation.screens.mechanics.trueorfalse

import android.view.View
import androidx.navigation.fragment.navArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseMechanicFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.trueorfalse.TrueOrFalseLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 4/1/21.
 */
class TrueOrFalseFragment : BaseMechanicFragment<TrueOrFalseViewModel>() {

    private val args: TrueOrFalseFragmentArgs by navArgs()

    private lateinit var trueOrFalseLayout: TrueOrFalseLayout

    override val viewModel by viewModel<TrueOrFalseViewModel> {
        parametersOf(args.data)
    }

    private val sharedViewModel by sharedViewModel<MechanicSessionViewModel>()

    override fun createMiddleContainerView() = TrueOrFalseLayout(requireContext()).also {
        trueOrFalseLayout = it
    }

    override fun initViews(view: View) {
        super.initViews(view)
        trueOrFalseLayout.onOptionClick = { option ->
            viewModel.processUiAction(TrueOrFalseUiAction.AnswerChosen(option.id))
        }
    }

    override fun onViewModelCreated(viewModel: TrueOrFalseViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                question?.let { question ->
                    trueOrFalseLayout.updateText(question.text)
                    trueOrFalseLayout.updateOptions(question.options)
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

    companion object {

        fun createInstance(args: TrueOrFalseFragmentArgs) =
                TrueOrFalseFragment().apply {
                    arguments = args.toBundle()
                }
    }
}