package com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.appearingcapture

import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.view.setPadding
import androidx.navigation.fragment.navArgs
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.AppearingCaptureItemEntity
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseMechanicFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.TimerView
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.capture.AppearingCaptureLayout
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.capture.CaptureCounterLayout
import dev.andrewbailey.diff.differenceOf
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 3/19/21.
 */
class AppearingCaptureFragment : BaseMechanicFragment<AppearingCaptureViewModel>() {

    private val args: AppearingCaptureFragmentArgs by navArgs()

    private lateinit var captureCounterLayout: CaptureCounterLayout
    private lateinit var appearingCaptureLayout: AppearingCaptureLayout
    private lateinit var timerView: TimerView

    private var listIsInitialized = false
    var appearingCaptureData = mutableListOf<AppearingCaptureItemEntity>()

    override val viewModel by viewModel<AppearingCaptureViewModel>() {
        parametersOf(args.data.items)
    }

    private val sharedViewModel by sharedViewModel<MechanicSessionViewModel>()

    override fun createBottomContainerView() = FrameLayout(requireContext()).apply {
        addView(TimerView(requireContext()).also {
            timerView = it
        }, FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            gravity = Gravity.CENTER
            setPadding(15.asPx)
        })
    }

    override fun createMiddleContainerView() = AppearingCaptureLayout(requireContext()).also {
        appearingCaptureLayout = it
    }

    override fun initViews(view: View) {
        super.initViews(view)
        appearingCaptureLayout.onItemClicked = {
            viewModel.processUiAction(AppearingCaptureUiAction.AnswerChosen(it))
        }
        captureCounterLayout = CaptureCounterLayout(requireContext())
        // mechanicsToolbarLayout.addViewToStub(captureCounterLayout) // TODO
        captureCounterLayout.setImageRes(R.drawable.butterfly)
    }

    override fun onViewModelCreated(viewModel: AppearingCaptureViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                captureCounterLayout.setCount(it.points)
                if (listIsInitialized)
                    calculateDifference(list)
                if (!listIsInitialized && list.isNotEmpty()) {
                    appearingCaptureLayout.setContent(list)
                    listIsInitialized = true
                }
                appearingCaptureData = list.toMutableList()
                timerView.updateTime(timeLeft)
            }
        }
        viewModel.navigationLiveData.observe(viewLifecycleOwner) { navigateTo ->
            when (navigateTo) {
                is NavigateTo.Result -> {
                    sharedViewModel.processUiAction(
                            MechanicSessionUiAction.NavigateToResult(
                                    navigateTo.points,
                                    navigateTo.from
                            )
                    )
                }
            }
        }
        viewModel.wrongAnswerLiveData.observe(viewLifecycleOwner) {
            it?.let(appearingCaptureLayout::setWrong)
        }
    }

    private fun calculateDifference(list: List<AppearingCaptureItemEntity>) {
        val diff = differenceOf(
                original = appearingCaptureData,
                updated = list,
                detectMoves = false
        )
        diff.applyDiff(
                remove = { index ->
                    appearingCaptureLayout.remove(appearingCaptureData[index])
                    appearingCaptureData.removeAt(index)
                },
                insert = { item, index ->
                    appearingCaptureLayout.add(item)
                },
                move = { _, _ ->
                }
        )
    }

    companion object {

        fun createInstance(args: AppearingCaptureFragmentArgs) =
                AppearingCaptureFragment().apply {
                    arguments = args.toBundle()
                }
    }
}