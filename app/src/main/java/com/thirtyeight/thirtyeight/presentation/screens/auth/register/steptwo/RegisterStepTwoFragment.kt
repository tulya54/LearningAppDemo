package com.thirtyeight.thirtyeight.presentation.screens.auth.register.steptwo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nikoloz14.myextensions.hideKeyboard
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentRegisterStepTwoBinding
import com.thirtyeight.thirtyeight.presentation.ext.bindToInputView
import com.thirtyeight.thirtyeight.presentation.ext.textChanges
import com.thirtyeight.thirtyeight.presentation.screens.auth.AuthViewModel
import com.thirtyeight.thirtyeight.presentation.ui.InputView
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class RegisterStepTwoFragment :
        ViewStateViewModelFragment<FragmentRegisterStepTwoBinding, RegisterStepTwoViewModel>() {

    private val inputMap = hashMapOf<Long, InputView>()

    override val layoutRes: Int
        get() = R.layout.fragment_register_step_two

    override val viewModel: RegisterStepTwoViewModel by viewModel()
    private val authViewModel by sharedViewModel<AuthViewModel>()

    override fun createBinding(view: View): FragmentRegisterStepTwoBinding =
            FragmentRegisterStepTwoBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            viewModel.processUiAction(RegisterStepTwoUiAction.NextClicked)
        }
        inputMap.putAll(
                arrayOf(
                        viewModel.emailInputId to binding.emailInputView,
                        viewModel.passwordInputId to binding.passwordInputView,
                        viewModel.repeatPasswordInputId to binding.confirmPasswordInputView,
                )
        )

        binding.emailInputView.editText.textChanges()
                .onEach {
                    viewModel.processUiAction(
                            RegisterStepTwoUiAction.InputTextChanged(
                                    viewModel.emailInputId,
                                    it.toString()
                            )
                    )
                }
                .launchIn(lifecycleScope)

        binding.passwordInputView.editText.textChanges()
                .onEach {
                    viewModel.processUiAction(
                            RegisterStepTwoUiAction.InputTextChanged(
                                    viewModel.passwordInputId,
                                    it.toString()
                            )
                    )
                }
                .launchIn(lifecycleScope)

        binding.confirmPasswordInputView.editText.textChanges()
                .onEach {
                    viewModel.processUiAction(
                            RegisterStepTwoUiAction.InputTextChanged(
                                    viewModel.repeatPasswordInputId,
                                    it.toString()
                            )
                    )
                }
                .launchIn(lifecycleScope)
    }

    override fun onViewModelCreated(viewModel: RegisterStepTwoViewModel) {
        super.onViewModelCreated(viewModel)
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) {
            with(it) {
                binding.nextButton.isEnabled = nextButtonEnabled
                form.inputs.forEach { inputState ->
                    inputMap[inputState.id]?.let { inputView ->
                        inputState.bindToInputView(inputView)
                    }
                }
            }
        }
        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            hideKeyboard()
            when (it) {
                is NavigateTo.LastStep -> {
                    authViewModel.registrationParams = authViewModel.registrationParams.copy(email = it.email, password = it.password)
                    val directions =
                            RegisterStepTwoFragmentDirections.actionNavRegisterStepTwoToNavRegisterStepLast()
                    findNavController().navigate(directions)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onDetach() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        super.onDetach()
    }
}