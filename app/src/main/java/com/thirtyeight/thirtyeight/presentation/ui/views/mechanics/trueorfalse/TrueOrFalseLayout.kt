package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.trueorfalse

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaOptionEntity
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 4/1/21.
 */
class TrueOrFalseLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val textView: TextView
    private val trueButton: View
    private val falseButton: View

    var onOptionClick: ((option: TriviaOptionEntity) -> Unit)? = null

    private var viewState = ViewState("", emptyList())

    private var answerPool = hashMapOf<View, TriviaOptionEntity>()

    init {
        val view = context.inflateLayout(R.layout.layout_true_or_false, this, true)
        textView = view.findViewById(R.id.text_view)
        trueButton = view.findViewById(R.id.true_button)
        falseButton = view.findViewById(R.id.false_button)
        trueButton.setOnClickListener {
            answerPool[it]?.let { option ->
                onOptionClick?.invoke(option)
            }
        }
        falseButton.setOnClickListener {
            answerPool[it]?.let { option ->
                onOptionClick?.invoke(option)
            }
        }
    }

    fun updateText(text: String) {
        viewState = viewState.copy(text = text)
        invalidateViewState()
    }

    fun updateOptions(options: List<TriviaOptionEntity>) {
        viewState = viewState.copy(options = options)
        invalidateViewState()
    }

    private fun invalidateViewState() {
        with(viewState) {
            textView.text = text
            if (answerPool.isEmpty() && options.isNotEmpty()) {
                answerPool[falseButton] = options[0]
                answerPool[trueButton] = options[1]
            }
        }
    }

    private data class ViewState(
            val text: String,
            val options: List<TriviaOptionEntity>
    )
}