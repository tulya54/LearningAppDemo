package com.thirtyeight.thirtyeight.presentation.screens.auth.register.laststep

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.findNavController
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentRegisterLastStepBinding
import com.thirtyeight.thirtyeight.presentation.screens.auth.AuthViewModel
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class RegisterLastStepFragment : ViewStateViewModelFragment<FragmentRegisterLastStepBinding, RegisterLastStepViewModel>() {

    override val layoutRes: Int
        get() = R.layout.fragment_register_last_step

    override val viewModel: RegisterLastStepViewModel by viewModel()
    private val authViewModel by sharedViewModel<AuthViewModel>()

    override fun createBinding(view: View): FragmentRegisterLastStepBinding =
            FragmentRegisterLastStepBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getStartedButton.setOnClickListener {
            viewModel.processUiAction(RegisterLastStepUiAction.NextClicked(authViewModel.registrationParams))
        }
    }

    override fun onViewModelCreated(viewModel: RegisterLastStepViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            when (it) {
                NavigateTo.App -> {
                    val extras = ActivityNavigator.Extras.Builder()
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .build()
                    val direction = RegisterLastStepFragmentDirections.actionNavRegisterStepLastToNavActivityMain()
                    findNavController().navigate(direction, extras)
                }
            }
        }
    }
}