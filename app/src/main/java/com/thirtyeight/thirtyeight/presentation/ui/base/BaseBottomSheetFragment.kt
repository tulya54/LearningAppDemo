package com.thirtyeight.thirtyeight.presentation.ui.base

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thirtyeight.thirtyeight.R

/**
 * Created by nikolozakhvlediani on 4/17/21.
 */
abstract class BaseBottomSheetFragment<VB : ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: VB? = null

    val binding: VB
        get() = _binding!!

    var onCancel: (() -> Unit)? = null

    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup?, attachToRoot: Boolean): VB

    protected open val skipCollapsedState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    protected open fun getRootView(): View = binding.root

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = createBinding(inflater, container, false).also { _binding = it }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancel?.invoke()
    }
}
