package com.thirtyeight.thirtyeight.presentation.screens.auth.register.stepone

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nikoloz14.myextensions.hideKeyboard
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.databinding.FragmentRegisterStepOneBinding
import com.thirtyeight.thirtyeight.presentation.ext.bindToInputView
import com.thirtyeight.thirtyeight.presentation.ext.textChanges
import com.thirtyeight.thirtyeight.presentation.screens.auth.AuthViewModel
import com.thirtyeight.thirtyeight.presentation.ui.InputView
import com.thirtyeight.thirtyeight.presentation.ui.base.ViewStateViewModelFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

/**
 * Created by nikolozakhvlediani on 4/16/21.
 */
class RegisterStepOneFragment :
        ViewStateViewModelFragment<FragmentRegisterStepOneBinding, RegisterStepOneViewModel>() {

    private val inputMap = hashMapOf<Long, InputView>()
    private val myCalendar: Calendar = Calendar.getInstance()

    override val layoutRes: Int
        get() = R.layout.fragment_register_step_one

    override val viewModel: RegisterStepOneViewModel by viewModel()
    private val authViewModel by sharedViewModel<AuthViewModel>()

    override fun createBinding(view: View): FragmentRegisterStepOneBinding =
            FragmentRegisterStepOneBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            viewModel.processUiAction(RegisterStepOneUiAction.NextClicked)
        }
        inputMap.putAll(arrayOf(
                viewModel.fullNameInputId to binding.fullNameInputView,
                viewModel.birthDateInputId to binding.birthdayInputView
        ))

        binding.fullNameInputView.editText.textChanges()
                .onEach { viewModel.processUiAction(RegisterStepOneUiAction.InputTextChanged(viewModel.fullNameInputId, it.toString())) }
                .launchIn(lifecycleScope)

        binding.birthdayInputView.editText.textChanges()
                .onEach { viewModel.processUiAction(RegisterStepOneUiAction.InputTextChanged(viewModel.birthDateInputId, it.toString())) }
                .launchIn(lifecycleScope)

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.birthdayInputView.text = viewModel.dateFormat.format(myCalendar.time)
            binding.birthdayInputView.invalidateViewState()
        }

        binding.birthdayInputView.setOnClickListener {
            DatePickerDialog(
                    requireContext(),
                    R.style.DatePickerDialogTheme,
                    dateListener,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
            ).also {
                it.datePicker.maxDate = Date().time
            }.show()
        }
    }

    override fun onViewModelCreated(viewModel: RegisterStepOneViewModel) {
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
                is NavigateTo.StepTwo -> {
                    authViewModel.registrationParams = authViewModel.registrationParams.copy(name = it.fullName, birthDate = it.birthDate)
                    val directions = RegisterStepOneFragmentDirections.actionNavRegisterStepOneToNavRegisterStepTwo()
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