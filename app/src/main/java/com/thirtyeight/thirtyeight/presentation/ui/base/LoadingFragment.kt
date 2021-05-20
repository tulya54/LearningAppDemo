package com.thirtyeight.thirtyeight.presentation.ui.base

import android.view.View
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentLoadingBinding

/**
 * Created by nikolozakhvlediani on 3/24/21.
 */
class LoadingFragment : BaseFragment<FragmentLoadingBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_loading

    override fun createBinding(view: View) =
            FragmentLoadingBinding.bind(view)

    companion object {

        fun createInstance() = LoadingFragment()
    }
}