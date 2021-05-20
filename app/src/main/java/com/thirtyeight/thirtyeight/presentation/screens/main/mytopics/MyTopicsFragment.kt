package com.thirtyeight.thirtyeight.presentation.screens.main.mytopics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.nikoloz14.myextensions.makeGone
import com.nikoloz14.myextensions.makeVisible
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentMyTopicsBinding
import com.thirtyeight.thirtyeight.presentation.screens.main.mytopics.tabs.alltopics.AllTopicsAdapter
import com.thirtyeight.thirtyeight.presentation.screens.main.mytopics.tabs.MyTopicsTabsAdapter
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
class MyTopicsFragment : ViewStateViewModelFragment<FragmentMyTopicsBinding, MyTopicsViewModel>() {
    private lateinit var secondTabLayout: View

    override val layoutRes: Int
        get() = R.layout.fragment_my_topics

    override val viewModel: MyTopicsViewModel by viewModel()

    override fun createBinding(view: View): FragmentMyTopicsBinding = FragmentMyTopicsBinding.bind(
        view
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTabs()
    }

    private fun setTabs() {
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText(resources.getString(R.string.all))
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText(resources.getString(R.string.Active))
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText(resources.getString(R.string.completed))
        )

        binding.viewPager.adapter = MyTopicsTabsAdapter(parentFragmentManager)
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(OnTabSelectedListener())

        secondTabLayout = LayoutInflater.from(requireContext()).inflate(
            R.layout.my_topics_second_tab,
            binding.tabLayout,
            false
        )
        binding.tabLayout.getTabAt(1)?.customView = secondTabLayout
        secondTabLayout.findViewById<View>(R.id.left_separator).makeGone()
        val tabTextView: TextView = secondTabLayout.findViewById(R.id.tab_title)
        tabTextView.text = resources.getString(R.string.Active)
    }

    inner class OnTabSelectedListener : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            binding.viewPager.currentItem = tab.position

            when (tab.position) {
                0 -> {
                    secondTabLayout.findViewById<View>(R.id.left_separator).makeGone()
                    secondTabLayout.findViewById<View>(R.id.right_separator).makeVisible()
                }
                1 -> {
                    secondTabLayout.findViewById<View>(R.id.left_separator).makeGone()
                    secondTabLayout.findViewById<View>(R.id.right_separator).makeGone()
                }
                2 -> {
                    secondTabLayout.findViewById<View>(R.id.left_separator).makeVisible()
                    secondTabLayout.findViewById<View>(R.id.right_separator).makeGone()
                }
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }
}