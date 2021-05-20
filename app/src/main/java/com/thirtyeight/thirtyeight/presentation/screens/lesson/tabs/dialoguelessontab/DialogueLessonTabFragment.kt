package com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.dialoguelessontab

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentDialogueLessonTabBinding
import com.thirtyeight.thirtyeight.presentation.screens.lesson.LessonViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DialogueLessonTabFragment : ViewStateViewModelFragment<FragmentDialogueLessonTabBinding, LessonViewModel>() {
    override val layoutRes: Int
        get() = R.layout.fragment_dialogue_lesson_tab

    override val viewModel: LessonViewModel by viewModel()

    override fun createBinding(view: View): FragmentDialogueLessonTabBinding = FragmentDialogueLessonTabBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pager.adapter = DialoguePageAdapter(requireActivity())
        binding.pager.orientation = ViewPager2.ORIENTATION_VERTICAL

        TabLayoutMediator(binding.tabLayout, binding.pager) { _, _ ->
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(OnTabSelectedListener())
    }

    inner class OnTabSelectedListener : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            setTabIndicators(tab.position)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }

    private fun setTabIndicators(position: Int) {
        val indicators = listOf(binding.firstTab, binding.secondTab, binding.thirdTab, binding.fourthTab)

        for (i in indicators.indices) {
            if (i == position) {
                indicators[i].background = AppCompatResources.getDrawable(requireContext(), R.drawable.background_dialogue_tab_active)
            } else {
                indicators[i].background = AppCompatResources.getDrawable(requireContext(), R.drawable.background_dialogue_tab_inactive)
            }
        }
    }
}