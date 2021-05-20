package com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs

import android.view.View
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentAudioLessonTabBinding
import com.thirtyeight.thirtyeight.databinding.FragmentDialogueLessonTabBinding
import com.thirtyeight.thirtyeight.databinding.FragmentLessonBinding
import com.thirtyeight.thirtyeight.presentation.screens.lesson.LessonViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DialogueLessonTabFragment : ViewStateViewModelFragment<FragmentDialogueLessonTabBinding, LessonViewModel>() {
    override val layoutRes: Int
        get() = R.layout.fragment_dialogue_lesson_tab

    override val viewModel: LessonViewModel by viewModel()

    override fun createBinding(view: View): FragmentDialogueLessonTabBinding = FragmentDialogueLessonTabBinding.bind(view)
}