package com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.dialoguelessontab.dialoguetabs

import android.view.View
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentDialogueLessonTabBinding
import com.thirtyeight.thirtyeight.databinding.FragmentFirstDialogueTabBinding
import com.thirtyeight.thirtyeight.presentation.screens.lesson.LessonViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstDialogueTabFragment : ViewStateViewModelFragment<FragmentFirstDialogueTabBinding, LessonViewModel>() {
    override val layoutRes: Int
        get() = R.layout.fragment_first_dialogue_tab

    override val viewModel: LessonViewModel by viewModel()

    override fun createBinding(view: View): FragmentFirstDialogueTabBinding = FragmentFirstDialogueTabBinding.bind(view)
}