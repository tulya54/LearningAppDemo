package com.thirtyeight.thirtyeight.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import com.nikoloz14.myextensions.makeGone
import com.nikoloz14.myextensions.makeVisible
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentBaseMechanicBinding
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
abstract class BaseMechanicFragment<VM : ViewStateViewModel<*, *, *>> :
        ViewStateViewModelFragment<FragmentBaseMechanicBinding, VM>() {

    override val layoutRes: Int
        get() = R.layout.fragment_base_mechanic

    override fun createBinding(view: View) = FragmentBaseMechanicBinding.bind(view)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding.topContainer.addOrHide(createTopContainerView())
        binding.middleContainer.addOrHide(createMiddleContainerView())
        createBottomContainerView()?.let {
            binding.root.addOrHide(
                    it,
                    LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            )
        }
        return view
    }

    protected open fun createTopContainerView(): View? = null
    protected open fun createMiddleContainerView(): View? = null
    protected open fun createBottomContainerView(): View? = null

    private fun ViewGroup.addOrHide(view: View?, layoutParams: ViewGroup.LayoutParams? = null) {
        view?.let {
            layoutParams?.let { params ->
                addView(it, params)
            } ?: addView(it)
            makeVisible()
        } ?: makeGone()
    }
}