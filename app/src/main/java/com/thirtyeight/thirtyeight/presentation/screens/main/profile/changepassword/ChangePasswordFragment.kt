package com.thirtyeight.thirtyeight.presentation.screens.main.profile.changepassword

import android.view.View
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentChangePasswordBinding
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by nikolozakhvlediani on 4/20/21.
 */
class ChangePasswordFragment : ViewStateViewModelFragment<FragmentChangePasswordBinding, ChangePasswordViewModel>() {

    override val layoutRes: Int
        get() = R.layout.fragment_change_password

    override val viewModel: ChangePasswordViewModel by viewModel()

    override fun createBinding(view: View): FragmentChangePasswordBinding = FragmentChangePasswordBinding.bind(view)
}