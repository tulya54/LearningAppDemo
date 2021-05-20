package com.thirtyeight.thirtyeight.presentation.screens.mechanics.captcha

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
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.captcha.CaptchaLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 3/31/21.
 */
class CaptchaFragment : BaseMechanicFragment<CaptchaViewModel>() {

    private val args: CaptchaFragmentArgs by navArgs()

    private lateinit var captchaLayout: CaptchaLayout

    override val viewModel by viewModel<CaptchaViewModel> {
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
                                viewModel.processUiAction(CaptchaUiAction.CheckClicked)
                            }
                        },
                        FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 45.asPx).apply {
                        }
                )
                val padding = resources.getDimension(R.dimen.mech_page_padding).toInt()
                setPadding(padding, padding, padding, padding)
            }

    override fun createMiddleContainerView() =
            CaptchaLayout(requireContext()).also {
                captchaLayout = it
            }

    override fun initViews(view: View) {
        super.initViews(view)
        captchaLayout.onItemClick = {
            viewModel.processUiAction(CaptchaUiAction.ItemClicked(it))
        }
    }

    override fun onViewModelCreated(viewModel: CaptchaViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                captchaLayout.setTitle(questionText, false)
                captchaLayout.setOptions(data, false)
                captchaLayout.setSelectedItems(selectedOptions)
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

        fun createInstance(args: CaptchaFragmentArgs) =
                CaptchaFragment().apply {
                    arguments = args.toBundle()
                }
    }
}