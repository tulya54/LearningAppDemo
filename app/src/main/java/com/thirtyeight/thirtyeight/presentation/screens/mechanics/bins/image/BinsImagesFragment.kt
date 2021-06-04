package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.image

import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.BinsFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins.BinsImagesLayout
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins.BinsLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
class BinsImagesFragment: BinsFragment<Int>() {

    private val args: BinsImagesFragmentArgs by navArgs()

    override fun createBinLayout(): BinsLayout<*, Int> = BinsImagesLayout(requireContext())

    override val viewModel by viewModel<BinsImagesViewModel> {
        parametersOf(args.data.data)
    }

    companion object {

        fun createInstance(args: BinsImagesFragmentArgs) =
                BinsImagesFragment().apply {
                    arguments = args.toBundle()
                }
    }


    override fun initViews(view: View) {
        super.initViews(view)
       // onModalWindow()
    }

    fun onModalWindow() {
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        dialog.setContentView(R.layout.modal_window_start_game)
        val tvName = dialog.findViewById<TextView>(R.id.tvName) as TextView
        tvName.text = "Image Bin"
        val tvDescription = dialog.findViewById<TextView>(R.id.tvDescription) as TextView
        tvDescription.text = "Explanation of the task"
        val btnStart = dialog.findViewById<TextView>(R.id.btnStart) as TextView
        btnStart.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}