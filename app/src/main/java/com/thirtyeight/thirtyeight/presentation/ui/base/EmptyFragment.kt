package com.thirtyeight.thirtyeight.presentation.ui.base

import android.view.View
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentEmptyBinding

/**
 * Created by nikolozakhvlediani on 3/30/21.
 */
class EmptyFragment : BaseFragment<FragmentEmptyBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_empty

    override fun createBinding(view: View) =
            FragmentEmptyBinding.bind(view)

    companion object {

        fun createInstance() = EmptyFragment()
    }
}