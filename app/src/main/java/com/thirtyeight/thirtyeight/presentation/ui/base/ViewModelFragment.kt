package com.thirtyeight.thirtyeight.presentation.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.nikoloz14.myextensions.showToast
import com.thirtyeight.thirtyeight.presentation.logic.BaseViewModel

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
abstract class ViewModelFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFragment<VB>() {

    abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewModelCreated(viewModel)
        viewModel.onCreate()
    }

    @CallSuper
    protected open fun onViewModelCreated(viewModel: VM) {
        viewModel.simpleErrorLiveData.observe(viewLifecycleOwner) {
            it?.let(::showToast)
        }
    }
}