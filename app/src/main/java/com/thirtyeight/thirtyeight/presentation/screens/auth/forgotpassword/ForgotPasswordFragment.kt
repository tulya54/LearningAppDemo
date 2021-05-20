package com.thirtyeight.thirtyeight.presentation.screens.auth.forgotpassword

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nikoloz14.myextensions.hideKeyboard
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentForgotPasswordBinding
import com.thirtyeight.thirtyeight.presentation.ext.bindToInputView
import com.thirtyeight.thirtyeight.presentation.ext.textChanges
import com.thirtyeight.thirtyeight.presentation.ui.InputView
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class ForgotPasswordFragment :
    ViewStateViewModelFragment<FragmentForgotPasswordBinding, ForgotPasswordViewModel>() {

    private val inputMap = hashMapOf<Long, InputView>()

    override val layoutRes: Int
        get() = R.layout.fragment_change_password

    override val viewModel: ForgotPasswordViewModel by viewModel()

    override fun createBinding(view: View) = FragmentForgotPasswordBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sendButton.setOnClickListener {
            viewModel.processUiAction(ForgotPasswordUiAction.SendClicked)
        }
        inputMap.putAll(
            arrayOf(
                viewModel.emailInputId to binding.emailInputView,
            )
        )

        binding.emailInputView.editText.textChanges()
            .onEach { viewModel.processUiAction(ForgotPasswordUiAction.InputTextChanged(viewModel.emailInputId, it.toString())) }
            .launchIn(lifecycleScope)
    }

    override fun onViewModelCreated(viewModel: ForgotPasswordViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                binding.sendButton.isEnabled = sendButtonEnabled
                form.inputs.forEach { inputState ->
                    inputMap[inputState.id]?.let { inputView ->
                        inputState.bindToInputView(inputView)
                    }
                }
                if (showSuccess) {
                    val bottomSheet = EmailWasSentBottomSheetFragment.createInstance()
                    bottomSheet.show(childFragmentManager, "EmailWasSent")
                    bottomSheet.onCancel = {
                        viewModel.processUiAction(ForgotPasswordUiAction.CloseClicked)
                    }
                }
            }
        }
        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            hideKeyboard()
            when (it) {
                NavigateTo.Finish -> {
                    findNavController().navigateUp()
                }
            }
        }
    }
}