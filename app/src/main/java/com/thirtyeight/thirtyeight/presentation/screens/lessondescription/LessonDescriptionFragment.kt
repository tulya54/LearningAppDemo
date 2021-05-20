package com.thirtyeight.thirtyeight.presentation.screens.lessondescription

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.fragment.app.FragmentContainerView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentLessonDescriptionBinding
import com.thirtyeight.thirtyeight.presentation.screens.lesson.LessonActivity
import com.thirtyeight.thirtyeight.presentation.screens.lesson.LessonFragment
import com.thirtyeight.thirtyeight.presentation.screens.lesson.LessonViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import com.thirtyeight.thirtyeight.util.OpenFragmentListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonDescriptionFragment : ViewStateViewModelFragment<FragmentLessonDescriptionBinding, LessonViewModel>() {
    override val layoutRes: Int
        get() = R.layout.fragment_lesson_description

    override val viewModel: LessonViewModel by viewModel()

    override fun createBinding(view: View): FragmentLessonDescriptionBinding = FragmentLessonDescriptionBinding.bind(view)

    private var openFragmentListener: OpenFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        openFragmentListener = context as? OpenFragmentListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonClickListener()

        binding.topicName.text = topicTitle("What is energy?")
    }

    private fun buttonClickListener() {
        binding.closeButton.setOnClickListener {
            requireActivity().finish()
        }

        binding.startButton.setOnClickListener {
            val intent = Intent(requireContext(), LessonActivity::class.java)
            startActivity(intent)
        }
    }

    private fun topicTitle(second: String, first: String = "In Topic "): SpannableStringBuilder {
        val topicTitle = SpannableStringBuilder()
        topicTitle.append(first)
        topicTitle.append(second)
        topicTitle.setSpan(ForegroundColorSpan(Color.parseColor("#000000")), 0, first.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        topicTitle.setSpan(ForegroundColorSpan(resources.getColor(R.color.color_primary)), first.length, topicTitle.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return topicTitle
    }

    override fun onDetach() {
        super.onDetach()
        openFragmentListener = null
    }
}