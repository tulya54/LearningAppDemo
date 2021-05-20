package com.thirtyeight.thirtyeight.presentation.screens.main.mytopics.tabs.alltopics

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentAllTopicsTabBinding
import com.thirtyeight.thirtyeight.presentation.screens.lessondescription.LessonDescriptionActivity
import com.thirtyeight.thirtyeight.presentation.screens.main.mytopics.MyTopicsViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllTopicsTabFragment : ViewStateViewModelFragment<FragmentAllTopicsTabBinding, MyTopicsViewModel>() {
    private lateinit var allTopicsTabsAdapter: AllTopicsAdapter

    override val layoutRes: Int
        get() = R.layout.fragment_all_topics_tab

    override val viewModel: MyTopicsViewModel by viewModel()

    override fun createBinding(view: View): FragmentAllTopicsTabBinding = FragmentAllTopicsTabBinding.bind(
        view
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAllTopics()
    }

    private fun setAllTopics() {
        viewModel.setAllTopics()

        val topicsLayout = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        allTopicsTabsAdapter = AllTopicsAdapter(requireContext()) {
            val intent = Intent(requireContext(), LessonDescriptionActivity::class.java)
            startActivity(intent)
        }

        binding.allTopicsRecycler.setHasFixedSize(false)
        binding.allTopicsRecycler.layoutManager = topicsLayout
        binding.allTopicsRecycler.adapter = allTopicsTabsAdapter

        viewModel.allTopics.observe(viewLifecycleOwner, {
            allTopicsTabsAdapter.setTopicsList(it)
        })
    }
}