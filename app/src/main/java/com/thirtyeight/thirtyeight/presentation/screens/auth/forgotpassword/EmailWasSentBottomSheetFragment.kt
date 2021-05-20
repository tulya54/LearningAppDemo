package com.thirtyeight.thirtyeight.presentation.screens.auth.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thirtyeight.thirtyeight.databinding.FragmentBottomSheetEmailWasSentBinding
import com.thirtyeight.thirtyeight.presentation.ui.base.BaseBottomSheetFragment

/**
 * Created by nikolozakhvlediani on 4/17/21.
 */
class EmailWasSentBottomSheetFragment : BaseBottomSheetFragment<FragmentBottomSheetEmailWasSentBinding>() {

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?, attachToRoot: Boolean) =
            FragmentBottomSheetEmailWasSentBinding.inflate(inflater, parent, attachToRoot)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.doneButton.setOnClickListener {
            dismissAllowingStateLoss()
            onCancel?.invoke()
        }
    }

    companion object {

        fun createInstance() = EmailWasSentBottomSheetFragment()
    }
}