package com.thirtyeight.thirtyeight.presentation.ui.base

import androidx.viewbinding.ViewBinding
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel

/**
 * Created by nikolozakhvlediani on 4/9/21.
 */
abstract class ViewStateViewModelActivity<VB : ViewBinding, VM : ViewStateViewModel<*, *, *>> :
        ViewModelActivity<VB, VM>()