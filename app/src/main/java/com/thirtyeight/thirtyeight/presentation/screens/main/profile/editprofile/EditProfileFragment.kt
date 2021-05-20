package com.thirtyeight.thirtyeight.presentation.screens.main.profile.editprofile

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentEditProfileBinding
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.CustomToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by nikolozakhvlediani on 4/22/21.
 */
class EditProfileFragment : ViewStateViewModelFragment<FragmentEditProfileBinding, EditProfileViewModel>(), CustomToolbar.RightIconClickEventListener {

    override val layoutRes: Int
        get() = R.layout.fragment_edit_profile

    override val viewModel: EditProfileViewModel by viewModel()

    override fun createBinding(view: View): FragmentEditProfileBinding = FragmentEditProfileBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onRightIconClicked() {
        val direction = EditProfileFragmentDirections.actionNavEditProfileToNavSettings()
        findNavController().navigate(direction)
    }
}