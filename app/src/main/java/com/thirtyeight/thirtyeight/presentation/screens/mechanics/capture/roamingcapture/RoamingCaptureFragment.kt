package com.thirtyeight.thirtyeight.presentation.screens.mechanics.capture.roamingcapture

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.setPadding
import androidx.navigation.fragment.navArgs
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.RoamingCaptureItemEntity
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseMechanicFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.TimerView
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.capture.CaptureCounterLayout
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.capture.RoamingCaptureLayout
import dev.andrewbailey.diff.differenceOf
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 3/19/21.
 */
class RoamingCaptureFragment :
        BaseMechanicFragment<RoamingCaptureViewModel>() {

    private val args: RoamingCaptureFragmentArgs by navArgs()

    private lateinit var captureCounterLayout: CaptureCounterLayout
    private lateinit var roamingCaptureLayout: RoamingCaptureLayout
    private lateinit var timerView: TimerView

    var listIsInitialized = false
    var roamingCaptureData = mutableListOf<RoamingCaptureItemEntity>()

    override val viewModel by viewModel<RoamingCaptureViewModel> {
        parametersOf(args.data.items)
    }

    private val sharedViewModel by sharedViewModel<MechanicSessionViewModel>()

    override fun createBottomContainerView() = FrameLayout(requireContext()).apply {
        addView(
                TimerView(requireContext()).also {
                    timerView = it
                }, FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER
            setPadding(15.asPx)
        })
    }

    override fun createMiddleContainerView() = RoamingCaptureLayout(requireContext()).also {
        roamingCaptureLayout = it
    }

    override fun initViews(view: View) {
        super.initViews(view)
        roamingCaptureLayout.onItemClicked = {
            viewModel.processUiAction(RoamingCaptureUiAction.AnswerChosen(it))
        }
        captureCounterLayout = CaptureCounterLayout(requireContext())
//        mechanicsToolbarLayout.addViewToStub(captureCounterLayout) // TODO
        captureCounterLayout.setImageRes(R.drawable.butterfly)
    }

    override fun onViewModelCreated(viewModel: RoamingCaptureViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                captureCounterLayout.setCount(it.points)
                if (listIsInitialized)
                    calculateDifference(list)
                if (!listIsInitialized && list.isNotEmpty()) {
                    roamingCaptureLayout.addData(list)
                    listIsInitialized = true
                }
                roamingCaptureData = list.toMutableList()
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
            it?.let(roamingCaptureLayout::setWrong)
        }
    }

    private fun calculateDifference(list: List<RoamingCaptureItemEntity>) {
        val diff = differenceOf(
                original = roamingCaptureData,
                updated = list,
                detectMoves = false
        )
        diff.applyDiff(
                remove = { index ->
                    roamingCaptureLayout.remove(roamingCaptureData[index])
                    roamingCaptureData.removeAt(index)
                },
                insert = { item, index ->
                    roamingCaptureLayout.add(item)
                },
                move = { _, _ ->
                }
        )
    }

    companion object {

        fun createInstance(args: RoamingCaptureFragmentArgs) =
                RoamingCaptureFragment().apply {
                    arguments = args.toBundle()
                }
    }
}