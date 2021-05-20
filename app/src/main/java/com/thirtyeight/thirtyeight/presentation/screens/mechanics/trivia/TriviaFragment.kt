package com.thirtyeight.thirtyeight.presentation.screens.mechanics.trivia

import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.navigation.fragment.navArgs
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.trueorfalse.TrueOrFalseFragmentArgs
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.CTextView
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseMechanicFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.trivia.TriviaLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
class TriviaFragment : BaseMechanicFragment<TriviaViewModel>() {

    private val args: TrueOrFalseFragmentArgs by navArgs()

    private lateinit var triviaLayout: TriviaLayout

    override val viewModel by viewModel<TriviaViewModel> {
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
                                viewModel.processUiAction(TriviaUiAction.CheckClicked)
                            }
                        },
                        FrameLayout.LayoutParams(MATCH_PARENT, 45.asPx).apply {
                        }
                )
                val padding = resources.getDimension(R.dimen.mech_page_padding).toInt()
                setPadding(padding, padding, padding, padding)
            }

    override fun createMiddleContainerView() = TriviaLayout(requireContext()).also {
        triviaLayout = it
    }

    override fun initViews(view: View) {
        super.initViews(view)
        triviaLayout.onOptionClick = { option ->
            viewModel.processUiAction(TriviaUiAction.AnswerChosen(option.id))
        }
    }

    override fun onViewModelCreated(viewModel: TriviaViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                question?.let { question ->
                    triviaLayout.updateText(question.text)
                    triviaLayout.updateOptions(question.options)
                }
                triviaLayout.updateChosenAnswers(chosenAnswers)
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

        fun createInstance(args: TriviaFragmentArgs) =
                TriviaFragment().apply {
                    arguments = args.toBundle()
                }
    }
}