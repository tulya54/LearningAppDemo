package com.thirtyeight.thirtyeight.presentation.screens.comingsoon

import android.view.View
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentComingSoonBinding
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by nikolozakhvlediani on 3/19/21.
 */
open class ComingSoonFragment :
        ViewModelFragment<FragmentComingSoonBinding, ComingSoonViewModel>() {

    override val viewModel by viewModel<ComingSoonViewModel>()

    override val layoutRes: Int
        get() = R.layout.fragment_coming_soon

    override fun createBinding(view: View) =
            FragmentComingSoonBinding.bind(view)
}