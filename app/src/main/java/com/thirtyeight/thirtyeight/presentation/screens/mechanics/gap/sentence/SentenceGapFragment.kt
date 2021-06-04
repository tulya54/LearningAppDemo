package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.sentence

import android.view.View
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapOptionData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.GapFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.SentenceGapLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
class SentenceGapFragment:
    GapFragment<SentenceGapData, SentenceGapOptionData, SentenceGapViewModel, SentenceGapLayout>() {

    private val args: SentenceGapFragmentArgs by navArgs()

    override val viewModel by viewModel<SentenceGapViewModel> {
        parametersOf(args.data.question)
    }

    override fun createGapLayout() = SentenceGapLayout(requireContext())

    companion object {
        fun createInstance(args: SentenceGapFragmentArgs) =
                SentenceGapFragment().apply {
                    arguments = args.toBundle()
                    Timber.tag("TAG").d("")
                }
    }

    override fun initViews(view: View) {
        super.initViews(view)
        onModalWindow()
    }

    fun onModalWindow() {
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        dialog.setContentView(R.layout.modal_window_start_game)
        val tvName = dialog.findViewById<TextView>(R.id.tvName) as TextView
        tvName.text = "Sentence gap"
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