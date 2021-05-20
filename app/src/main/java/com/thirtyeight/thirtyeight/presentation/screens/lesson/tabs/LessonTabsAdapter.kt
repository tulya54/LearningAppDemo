package com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.dialoguelessontab.DialogueLessonTabFragment

class LessonTabsAdapter(fa: FragmentManager): FragmentStatePagerAdapter(fa) {

    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AudioLessonTabFragment()
            1 -> DialogueLessonTabFragment()
            2 -> VideoLessonTabFragment()
            3 -> ArticleLessonTabFragment()
            else -> ArticleLessonTabFragment()
        }
    }
}