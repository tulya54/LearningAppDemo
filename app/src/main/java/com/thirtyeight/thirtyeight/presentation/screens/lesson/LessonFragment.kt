package com.thirtyeight.thirtyeight.presentation.screens.lesson

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.nikoloz14.myextensions.makeGone
import com.nikoloz14.myextensions.makeVisible
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentLessonBinding
import com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.LessonTabsAdapter
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.MechanicsActivity
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonFragment : ViewStateViewModelFragment<FragmentLessonBinding, LessonViewModel>() {
    private lateinit var dialogueTabLayout: View
    private lateinit var videoTabLayout: View

    override val layoutRes: Int
        get() = R.layout.fragment_lesson

    override val viewModel: LessonViewModel by viewModel()

    override fun createBinding(view: View): FragmentLessonBinding = FragmentLessonBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLessonTabs()
        setButtonsListener()

        requireActivity().window.statusBarColor = resources.getColor(R.color.white)
        requireActivity().window.navigationBarColor = resources.getColor(R.color.white)
    }

    private fun setButtonsListener() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.startButton.setOnClickListener {
            val intent = Intent(requireContext(), MechanicsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setLessonTabs() {
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText("\uD83D\uDD0A ${resources.getString(R.string.audio)}")
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText("\uD83D\uDCAC ${resources.getString(R.string.dialogue)}")
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText("\uD83C\uDFA5 ${resources.getString(R.string.video)}")
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText("\uD83D\uDCD6 ${resources.getString(R.string.article)}")
        )

        binding.viewPager.adapter = LessonTabsAdapter(parentFragmentManager)
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(OnTabSelectedListener())
//        binding.viewPager.currentItem = 3

        dialogueTabLayout = LayoutInflater.from(requireContext()).inflate(
            R.layout.lesson_dialogue_tab,
            binding.tabLayout,
            false
        )
        binding.tabLayout.getTabAt(1)?.customView = dialogueTabLayout
        dialogueTabLayout.findViewById<View>(R.id.left_separator).makeGone()
        val dialogueTabTextView: TextView = dialogueTabLayout.findViewById(R.id.tab_title)
        dialogueTabTextView.text = "\uD83D\uDCAC ${resources.getString(R.string.dialogue)}"

        videoTabLayout = LayoutInflater.from(requireContext()).inflate(
            R.layout.lesson_video_tab,
            binding.tabLayout,
            false
        )
        binding.tabLayout.getTabAt(2)?.customView = videoTabLayout
        val videoTabTextView: TextView = videoTabLayout.findViewById(R.id.tab_title)
        videoTabTextView.text = "\uD83C\uDFA5 ${resources.getString(R.string.video)}"
    }

    inner class OnTabSelectedListener : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            binding.viewPager.currentItem = tab.position

            when (tab.position) {
                0 -> {
                    dialogueTabLayout.findViewById<View>(R.id.left_separator).makeGone()
                    dialogueTabLayout.findViewById<View>(R.id.right_separator).makeVisible()
                    videoTabLayout.findViewById<View>(R.id.right_separator).makeVisible()
                }
                1 -> {
                    dialogueTabLayout.findViewById<View>(R.id.left_separator).makeGone()
                    dialogueTabLayout.findViewById<View>(R.id.right_separator).makeGone()
                    videoTabLayout.findViewById<View>(R.id.right_separator).makeVisible()
                }
                2 -> {
                    dialogueTabLayout.findViewById<View>(R.id.left_separator).makeVisible()
                    dialogueTabLayout.findViewById<View>(R.id.right_separator).makeGone()
                    videoTabLayout.findViewById<View>(R.id.right_separator).makeGone()
                }
                3 -> {
                    dialogueTabLayout.findViewById<View>(R.id.left_separator).makeVisible()
                    dialogueTabLayout.findViewById<View>(R.id.right_separator).makeVisible()
                    videoTabLayout.findViewById<View>(R.id.right_separator).makeGone()
                }
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }
}