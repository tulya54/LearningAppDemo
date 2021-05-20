package com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.dialoguelessontab.dialoguetabs

import android.view.View
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.*
import com.thirtyeight.thirtyeight.presentation.screens.lesson.LessonViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FourthDialogueTabFragment : ViewStateViewModelFragment<FragmentFourthDialogueTabBinding, LessonViewModel>() {
    override val layoutRes: Int
        get() = R.layout.fragment_fourth_dialogue_tab

    override val viewModel: LessonViewModel by viewModel()

    override fun createBinding(view: View): FragmentFourthDialogueTabBinding = FragmentFourthDialogueTabBinding.bind(view)
}