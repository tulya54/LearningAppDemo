package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.sentence.SentenceGapFragment
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionActivity
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionUiAction
import com.thirtyeight.thirtyeight.presentation.screens.mechanicsession.MechanicSessionViewModel
import com.thirtyeight.thirtyeight.presentation.ui.CTextView
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseMechanicFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.GapLayout
import com.thirtyeight.thirtyeight.util.SpannableTools
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
abstract class GapFragment<GapData, OptionData, VM : GapViewModel<GapData, OptionData>, GL : GapLayout<*, GapData, OptionData>> :
        BaseMechanicFragment<VM>() {

    private val sharedViewModel by sharedViewModel<MechanicSessionViewModel>()

    private lateinit var gapLayout: GL

    abstract fun createGapLayout(): GL

    val thisCLass = this
    var btnGo: CTextView? = null

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
                btnGo = CTextView(contextWrapper, null, R.style.SentenceGapButtonReady).apply {
                    setText(R.string.ready)
                    setOnClickListener {
                        viewModel.processUiAction(GapUiAction.CheckClicked)
                    }
                }
                val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    resources.getDimension(R.dimen._40sdp).toInt())
                params.setMargins(0,0,0,resources.getDimension(R.dimen._15sdp).toInt())
                addView(btnGo, params)
               // val leftOrRight = resources.getDimension(R.dimen._20sdp).toInt()
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
                    if (thisCLass is SentenceGapFragment) {
                        Timber.tag("TAG").d("GOOD")
                        if (!isSentenceGap) {
                            isSentenceGap = !isSentenceGap
                            onSentenceGapsButton(it)
                        } else {
                            Toast.makeText(context, "Beta test", Toast.LENGTH_SHORT).show()
                        }
                    } else {
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

    private var isSentenceGap = false

    //  My change
    open fun onSentenceGapsButton(result: NavigateTo.Result) {
        val points = result.points
        val from = result.from
        val resultList = result.resultList
        if (points == from) {
            gapLayout.goodResult()
        } else {
            gapLayout.wrongResult(points, from, resultList!!)
        }
        val mechanicSessionActivity = activity as MechanicSessionActivity?
        mechanicSessionActivity?.let { activity ->
            val spannable = SpannableTools.getSpannable(activity,
                "СORRECT $points", "/$from",
                if (points == from) R.color.green_dark else R.color.red_dark)
            activity.onChangeTitleToolbar(spannable)
            btnGo?.let {
                it.background = ContextCompat.getDrawable(activity,
                    if (points == from) R.drawable.background_btn_green_dark
                    else R.drawable.background_btn_red_dark)
                it.text = "CONTINUE"
            }
        }
    }
}