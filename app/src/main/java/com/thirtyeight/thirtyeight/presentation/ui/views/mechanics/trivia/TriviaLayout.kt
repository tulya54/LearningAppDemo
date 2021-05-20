package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.trivia

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.forEach
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.trivia.TriviaOptionEntity
import com.thirtyeight.thirtyeight.presentation.ext.getColorFromAttr
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.presentation.ui.CTextView

/**
 * Created by nikolozakhvlediani on 3/26/21.
 */
class TriviaLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val textView: TextView
    private val linear: LinearLayout

    var onOptionClick: ((option: TriviaOptionEntity) -> Unit)? = null

    private var viewState = ViewState("", emptyList(), emptyList())

    private var viewPool = hashMapOf<TextView, Long>()

    init {
        val view = context.inflateLayout(R.layout.layout_trivia, this, true)
        textView = view.findViewById(R.id.text_view)
        linear = view.findViewById(R.id.options)
    }

    fun updateText(text: String) {
        viewState = viewState.copy(text = text)
        invalidateViewState()
    }

    fun updateOptions(options: List<TriviaOptionEntity>) {
        viewState = viewState.copy(options = options)
        invalidateViewState()
    }

    fun updateChosenAnswers(chosenOptions: List<Long>) {
        viewState = viewState.copy(chosenOptions = chosenOptions)
        invalidateViewState()
    }

    private fun invalidateViewState() {
        with(viewState) {
            textView.text = text
            if (linear.childCount == 0) {
                val contextWrapper =
                        ContextThemeWrapper(context, R.style.MechButtonWithPrimaryBorderStyle)
                options.forEachIndexed { index, triviaOptionEntity ->
                    val view =
                            CTextView(
                                    contextWrapper,
                                    null,
                                    R.style.MechButtonWithPrimaryBorderStyle
                            ).apply {
                                text = triviaOptionEntity.text
                                setOnClickListener {
                                    onOptionClick?.invoke(triviaOptionEntity)
                                }
                            }
                    linear.addView(
                            view,
                            LinearLayout.LayoutParams(MATCH_PARENT, 45.asPx).apply {
                                topMargin = if (index == 0) 0 else 10.asPx
                            }
                    )
                    viewPool[view] = triviaOptionEntity.id
                }
            }
            linear.forEach {
                val selected = chosenOptions.contains(viewPool[it])
                it.setBackgroundResource(
                        if (selected)
                            R.drawable.background_mech_button_primary
                        else
                            R.drawable.background_mech_button_with_primary_variant_borders
                )
                val textColor = if (selected)
                    context.getColorFromAttr(R.attr.colorOnPrimary)
                else
                    context.getColorFromAttr(R.attr.colorMechOnUnselectedButton)
                (it as TextView).setTextColor(textColor)
            }
        }
    }

    private data class ViewState(
            val text: String,
            val options: List<TriviaOptionEntity>,
            val chosenOptions: List<Long>
    )
}