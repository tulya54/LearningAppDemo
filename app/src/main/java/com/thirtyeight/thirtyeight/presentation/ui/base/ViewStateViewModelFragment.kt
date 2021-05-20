package com.thirtyeight.thirtyeight.presentation.ui.base

import androidx.viewbinding.ViewBinding
import com.thirtyeight.thirtyeight.presentation.logic.ViewStateViewModel

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
abstract class ViewStateViewModelFragment<VB : ViewBinding, VM : ViewStateViewModel<*, *, *>> :
        ViewModelFragment<VB, VM>()