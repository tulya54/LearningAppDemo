package com.thirtyeight.thirtyeight.presentation.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.thirtyeight.thirtyeight.presentation.logic.BaseViewModel

/**
 * Created by nikolozakhvlediani on 4/9/21.
 */
abstract class ViewModelActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity<VB>() {

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onViewModelCreated(viewModel)
        viewModel.onCreate()
    }

    @CallSuper
    protected open fun onViewModelCreated(viewModel: VM) {
    }
}