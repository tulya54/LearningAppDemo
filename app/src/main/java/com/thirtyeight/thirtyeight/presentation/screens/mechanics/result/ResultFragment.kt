package com.thirtyeight.thirtyeight.presentation.screens.mechanics.result

import android.view.View
import androidx.navigation.fragment.navArgs
import com.nikoloz14.myextensions.makeVisibleOrGone
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentResultBinding
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewModelFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by nikolozakhvlediani on 3/13/21.
 */
class ResultFragment : ViewModelFragment<FragmentResultBinding, ResultViewModel>() {

    override val layoutRes: Int
        get() = R.layout.fragment_result

    override val viewModel by viewModel<ResultViewModel>()

    val sharedViewModel by sharedViewModel<MechanicSessionViewModel>()

    override fun createBinding(view: View) = FragmentResultBinding.bind(view)

    private val args: ResultFragmentArgs by navArgs()

    override fun initViews(view: View) {
        super.initViews(view)
        with(binding) {
            nextButton.makeVisibleOrGone(args.showNext)
            nextButton.setOnClickListener {
                sharedViewModel.processUiAction(MechanicSessionUiAction.LoadNextClicked)
            }
            tryAgainButton.setOnClickListener {
                sharedViewModel.processUiAction(MechanicSessionUiAction.RetryClicked)
            }
            mainPageButton.setOnClickListener {
                sharedViewModel.processUiAction(MechanicSessionUiAction.CloseClicked)
            }
            resultTextView.text = "${args.points}/${args.from}"
        }
    }

    companion object {

        fun createInstance(args: ResultFragmentArgs) =
                ResultFragment().apply {
                    arguments = args.toBundle()
                }
    }
}