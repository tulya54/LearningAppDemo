package com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.dialoguelessontab.dialoguetabs

import android.view.View
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentDialogueLessonTabBinding
import com.thirtyeight.thirtyeight.databinding.FragmentFirstDialogueTabBinding
import com.thirtyeight.thirtyeight.databinding.FragmentSecondDialogueTabBinding
import com.thirtyeight.thirtyeight.presentation.screens.lesson.LessonViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SecondDialogueTabFragment : ViewStateViewModelFragment<FragmentSecondDialogueTabBinding, LessonViewModel>() {
    override val layoutRes: Int
        get() = R.layout.fragment_second_dialogue_tab

    override val viewModel: LessonViewModel by viewModel()

    override fun createBinding(view: View): FragmentSecondDialogueTabBinding = FragmentSecondDialogueTabBinding.bind(view)
}