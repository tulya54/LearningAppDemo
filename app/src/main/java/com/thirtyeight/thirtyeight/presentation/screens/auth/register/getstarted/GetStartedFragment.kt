package com.thirtyeight.thirtyeight.presentation.screens.auth.register.getstarted

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nikoloz14.myextensions.showToast
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentGetStartedBinding
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class GetStartedFragment : ViewStateViewModelFragment<FragmentGetStartedBinding, GetStartedViewModel>() {

    override val layoutRes: Int
        get() = R.layout.fragment_get_started

    override val viewModel: GetStartedViewModel by viewModel()

    override fun createBinding(view: View): FragmentGetStartedBinding =
            FragmentGetStartedBinding.bind(view)

    // TODO tmp
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpWithEmailButton.setOnClickListener {
            showToast("Welcome!")
            val direction = GetStartedFragmentDirections.actionNavGetStartedToNavRegisterStepOne()
            findNavController().navigate(direction)
        }
    }
}