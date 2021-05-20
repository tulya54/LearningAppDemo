package com.thirtyeight.thirtyeight.presentation.screens.main.discover

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentDiscoverBinding
import com.thirtyeight.thirtyeight.presentation.screens.lesson.LessonActivity
import com.thirtyeight.thirtyeight.presentation.screens.lessondescription.LessonDescriptionActivity
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
class DiscoverFragment : ViewStateViewModelFragment<FragmentDiscoverBinding, DiscoverViewModel>() {
    private lateinit var discoverAdapter: DiscoverAdapter

    override val layoutRes: Int
        get() = R.layout.fragment_discover

    override val viewModel: DiscoverViewModel by viewModel()

    override fun createBinding(view: View): FragmentDiscoverBinding = FragmentDiscoverBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()
    }

    private fun setUpRecycler() {
        viewModel.setDiscoverTopics()

        val topicsLayout = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        discoverAdapter = DiscoverAdapter(requireContext()) {
            val intent = Intent(requireContext(), LessonDescriptionActivity::class.java)
            startActivity(intent)
        }

        binding.topicsRecycler.setHasFixedSize(false)
        binding.topicsRecycler.layoutManager = topicsLayout
        binding.topicsRecycler.adapter = discoverAdapter

        viewModel.discoverTopics.observe(viewLifecycleOwner, {
            discoverAdapter.setTopicsList(it)
        })
    }
}