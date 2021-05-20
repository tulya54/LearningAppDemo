package com.thirtyeight.thirtyeight.presentation.screens.auth.welcome

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentWelcomeBinding
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseFragment

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_welcome

    override fun createBinding(view: View): FragmentWelcomeBinding =
            FragmentWelcomeBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            signInButton.setOnClickListener {
                val direction = WelcomeFragmentDirections.actionNavWelcomeToNavLogin()
                findNavController().navigate(direction)
            }
            registerButton.setOnClickListener {
                val direction = WelcomeFragmentDirections.actionNavWelcomeToNavGetStarted()
                findNavController().navigate(direction)
            }
        }
    }
}