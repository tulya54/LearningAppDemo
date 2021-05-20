package com.thirtyeight.thirtyeight.presentation.screens.main.mytopics.tabs

import android.view.View
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentActiveTopicsTabBinding
import com.thirtyeight.thirtyeight.databinding.FragmentAllTopicsTabBinding
import com.thirtyeight.thirtyeight.presentation.screens.main.mytopics.MyTopicsViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActiveTopicsTabFragment : ViewStateViewModelFragment<FragmentActiveTopicsTabBinding, MyTopicsViewModel>() {
    override val layoutRes: Int
        get() = R.layout.fragment_active_topics_tab

    override val viewModel: MyTopicsViewModel by viewModel()

    override fun createBinding(view: View): FragmentActiveTopicsTabBinding = FragmentActiveTopicsTabBinding.bind(
        view
    )
}