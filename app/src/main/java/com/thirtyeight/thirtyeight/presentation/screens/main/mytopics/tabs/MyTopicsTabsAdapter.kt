package com.thirtyeight.thirtyeight.presentation.screens.main.mytopics.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.screens.main.mytopics.tabs.alltopics.AllTopicsTabFragment

class MyTopicsTabsAdapter(fa: FragmentManager): FragmentStatePagerAdapter(fa) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AllTopicsTabFragment()
            1 -> ActiveTopicsTabFragment()
            2 -> CompletedTopicsTabFragment()
            else -> AllTopicsTabFragment()
        }
    }
}