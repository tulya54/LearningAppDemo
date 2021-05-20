package com.thirtyeight.thirtyeight.presentation.screens.main.profile.settings

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentSettingsBinding
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
class SettingsFragment : ViewStateViewModelFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override val layoutRes: Int
        get() = R.layout.fragment_settings

    override val viewModel: SettingsViewModel by viewModel()

    override fun createBinding(view: View): FragmentSettingsBinding = FragmentSettingsBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            changePasswordSettingsItemLayout.setOnClickListener {
                viewModel.processUiAction(SettingsUiAction.ChangePasswordClicked)
            }
        }
    }

    override fun onViewModelCreated(viewModel: SettingsViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            when (it) {
                NavigateTo.ChangePassword -> {
                    val direction = SettingsFragmentDirections.actionNavSettingsToNavChangePassword()
                    findNavController().navigate(direction)
                }
            }
        }
    }
}