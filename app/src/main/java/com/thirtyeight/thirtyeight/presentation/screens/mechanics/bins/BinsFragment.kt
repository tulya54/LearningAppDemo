package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionActivity
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseMechanicFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins.BinsLayout
import com.thirtyeight.thirtyeight.util.SpannableTools
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
abstract class BinsFragment<BinData>: BaseMechanicFragment<BinsViewModel<BinData>>() {

    private val sharedViewModel by sharedViewModel<MechanicSessionViewModel>()

    private lateinit var binsLayout: BinsLayout<*, BinData>
    private lateinit var leftButton: View
    private lateinit var rightButton: View

    //  My change
    private var timer: CountDownTimer? = null
    private var tvTimer: TextView? = null
    private var layoutBinsButtons: View? = null
    private var pbLoader: ProgressBar? = null

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
        layoutBinsButtons = context.inflateLayout(R.layout.layout_bins_buttons)
        leftButton = layoutBinsButtons?.findViewById(R.id.left_button)!!
        rightButton = layoutBinsButtons?.findViewById(R.id.right_button)!!
        val progressBar = layoutBinsButtons?.findViewById(R.id.progressBar) as View?
        progressBar?.let {
            tvTimer = it.findViewById(R.id.tvTimer)
            pbLoader = it.findViewById(R.id.progressBar1)
        }
        return layoutBinsButtons
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        leftButton.setOnClickListener {
            viewModel.processUiAction(BinsUiAction.LeftArrowClicked)
            val isBtnDisable = binsLayout.moveFallingView(left = true)
            context?.let {
                if (isBtnDisable) {
                    leftButton.background = ContextCompat.getDrawable(it, R.drawable.background_btn_ready_disable)
                }
                rightButton.background = ContextCompat.getDrawable(it, R.drawable.background_btn_ready)
            }

        }
        rightButton.setOnClickListener {
            viewModel.processUiAction(BinsUiAction.RightArrowClicked)
            val isBtnDisable = binsLayout.moveFallingView(right = true)
            context?.let {
                if (isBtnDisable) {
                    rightButton.background = ContextCompat.getDrawable(it, R.drawable.background_btn_ready_disable)
                }
                leftButton.background = ContextCompat.getDrawable(it, R.drawable.background_btn_ready)
            }
        }

        //  TEMPORARY WIDGETS
        binsLayout.ivItem1?.setOnClickListener { v ->
            viewModel.processUiAction(BinsUiAction.Position0)
            binsLayout.selectPosition(0)
            context?.let {
                leftButton.background = ContextCompat.getDrawable(it, R.drawable.background_btn_ready_disable)
                rightButton.background = ContextCompat.getDrawable(it, R.drawable.background_btn_ready)
            }
        }
        binsLayout.ivItem2?.setOnClickListener { v ->
            viewModel.processUiAction(BinsUiAction.Position1)
            binsLayout.selectPosition(1)
            context?.let {
                rightButton.background = ContextCompat.getDrawable(it, R.drawable.background_btn_ready)
                leftButton.background = ContextCompat.getDrawable(it, R.drawable.background_btn_ready)
            }
        }
        binsLayout.ivItem3?.setOnClickListener { v ->
            viewModel.processUiAction(BinsUiAction.Position2)
            binsLayout.selectPosition(2)
            context?.let {
                rightButton.background = ContextCompat.getDrawable(it, R.drawable.background_btn_ready_disable)
                leftButton.background = ContextCompat.getDrawable(it, R.drawable.background_btn_ready)
            }
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
            onResult()
            when (it) {
                is NavigateTo.Result -> {
                    onResult(it.points >= it.from)
                }
//                is NavigateTo.Result -> {
//                    sharedViewModel.processUiAction(
//                            MechanicSessionUiAction.NavigateToResult(
//                                    it.points,
//                                    it.from
//                            )
//                    )
//                }
            }
        }
    }

    fun onResult(isGood: Boolean = false) {
        layoutBinsButtons?.visibility = View.GONE
        binsLayout.onResult(isGood)

        val mechanicSessionActivity = activity as MechanicSessionActivity?
        mechanicSessionActivity?.let { activity ->
            if (isGood) {
                activity.onChangeTitleToolbar(SpannableTools
                    .getSpannable(activity, "GOOD SPEED!", R.color.green_dark))
            } else {
                activity.onChangeTitleToolbar(SpannableTools
                    .getSpannable(activity, "TRY FASTER...", R.color.red_dark))
            }
        }
    }

    fun onStartTimer() {
        viewModel.onStartFalling()
        //
        val totalMillisecond = 60000L
        timer = object: CountDownTimer(totalMillisecond, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = millisUntilFinished / 1000
                val percent = (totalMillisecond.toInt() / 1000) - progress.toInt()
                val text = "0:${percent}"
                tvTimer?.text = text
                pbLoader?.progress = percent
                if (percent >= 30) {
                    activity?.let {
                        tvTimer?.setTextColor(ContextCompat.getColor(it, R.color.white))
                    }
                }
            }

            override fun onFinish() {
                tvTimer?.text = "0:0"
                onStopTimer()
            }
        }
        timer?.start()
    }
    private fun onStopTimer() {
        timer?.let {
            it.cancel()
            timer = null
        }
    }
}