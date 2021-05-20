package com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.dialoguelessontab

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.ArticleLessonTabFragment
import com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.AudioLessonTabFragment
import com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.VideoLessonTabFragment
import com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.dialoguelessontab.dialoguetabs.FirstDialogueTabFragment
import com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.dialoguelessontab.dialoguetabs.FourthDialogueTabFragment
import com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.dialoguelessontab.dialoguetabs.SecondDialogueTabFragment
import com.thirtyeight.thirtyeight.presentation.screens.lesson.tabs.dialoguelessontab.dialoguetabs.ThirdDialogueTabFragment

class DialoguePageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstDialogueTabFragment()
            1 -> SecondDialogueTabFragment()
            2 -> ThirdDialogueTabFragment()
            3 -> FourthDialogueTabFragment()
            else -> FirstDialogueTabFragment()
        }
    }

}