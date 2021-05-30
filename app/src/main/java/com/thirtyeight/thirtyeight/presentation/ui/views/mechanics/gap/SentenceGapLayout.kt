package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import com.nex3z.flowlayout.FlowLayout
import com.nikoloz14.myextensions.asDp
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapData
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapItem
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence.SentenceGapOptionData
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.presentation.ui.CTextView
import com.thirtyeight.thirtyeight.util.drag.DropListener
import timber.log.Timber

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
class SentenceGapLayout constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    GapLayout<CTextView, SentenceGapData, SentenceGapOptionData>(context, attrs, defStyleAttr) {

    override val gapDimensions: Dimensions by lazy { Dimensions(100.asPx, context.resources.getDimension(R.dimen._30sdp).toInt()) }
    private val gapButtonContextWrapper = ContextThemeWrapper(context, R.style.SentenceGapSelectText)
    private val sentenceTextContextWrapper = ContextThemeWrapper(context, R.style.MechanicTextViewStyle)

    override fun addGaps(gapData: SentenceGapData, clickListener: (gapView: CTextView, index: Int) -> Unit) {
        val frame = context.inflateLayout(R.layout.part_sentence_gaps) as ConstraintLayout
        val flowLayout = frame.findViewById(R.id.root) as androidx.constraintlayout.helper.widget.Flow
        val v1 = frame.findViewById(R.id.v1) as View
        val v2 = frame.findViewById(R.id.v2) as View
        val v3 = frame.findViewById(R.id.v3) as View
        val v4 = frame.findViewById(R.id.v4) as View
        //val flowLayout = context.inflateLayout(R.layout.part_sentence_gaps) as FlowLayout
        var counter = 0
        var count = 0
        val id = 7000
        val ids = IntArray(gapData.data.size)
        gapData.data.forEach {
            when (it) {
                is SentenceGapItem.Gap -> {
                    val index = counter
                    val gap = createGapView().apply {
                        setOnClickListener {
                            clickListener(this, index)
                        }
                        clearGap(this)
                    }
                    val leftOrRight = context.resources.getDimension(R.dimen._20sdp).toInt()
                    gap.setPadding(leftOrRight, 0, leftOrRight, 0)
                    counter++
                    gaps.add(gap)
                    availableGaps.add(true)
                    gap.id = id + count
                    ids[count] = id + count
                    val params = LinearLayout.LayoutParams(WRAP_CONTENT, gapDimensions.height)
                    frame.addView(gap, params)
                }
                is SentenceGapItem.Word -> {
                    val wordView = CTextView(
                            sentenceTextContextWrapper,
                            null,
                            R.style.MechanicTextViewStyle
                    ).apply {
                        text = it.text
                    }
                    wordView.id = id + count
                    ids[count] = id + count
                    val params = LinearLayout.LayoutParams(WRAP_CONTENT, gapDimensions.height)
                    wordView.gravity = Gravity.CENTER
                    frame.addView(wordView, params)
                }
            }
            count++
        }
        flowLayout.referencedIds = ids
        gapContainer.addView(
            frame,
                LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                    gravity = Gravity.TOP
                }
        )
        flowLayout.post {
            val flowRowHeight = context.resources.getDimension(R.dimen._34sdp).toInt()
            val layoutHeight = flowLayout.height
            val result = layoutHeight / flowRowHeight
            v1.visibility = if (result > 0) View.VISIBLE else View.GONE
            v2.visibility = if (result > 1) View.VISIBLE else View.GONE
            v3.visibility = if (result > 2) View.VISIBLE else View.GONE
            v4.visibility = if (result > 3) View.VISIBLE else View.GONE
            Timber.tag("TAG").d("Flow row count: $result")
        }
        Timber.tag("TAG").d("")
    }

    private fun getDimensionDP(numb: Int) : Int {
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, numb.toFloat(), getResources().getDisplayMetrics());
        return Math.round(px)
    }

    private fun getDimensionPX(numb: Int) : Int {
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, numb.toFloat(), getResources().getDisplayMetrics());
        return Math.round(px)
    }

    override fun createGapView(): CTextView {
        return CTextView(
                gapButtonContextWrapper,
                null,
                R.style.MechGapButtonStyle
        )
    }

    override fun clearGap(gap: CTextView) {
        gap.text = ""
        gap.setTextColor(ContextCompat.getColor(context, R.color.black))
        gap.background = ContextCompat.getDrawable(context, R.drawable.background_sentence_gap_unselected_text)
        gap.setOnDragListener(
                DropListener {
                    chooseOptionForGap(
                            gaps.indexOf(gap),
                            optionData[draggedItem]!!,
                            gap
                    )
                }
        )
    }

    override fun setDataToGap(gap: CTextView, dataGap: SentenceGapOptionData) {
        gap.text = dataGap.text
    }

    //  My change
    override fun changeUIGapSelected(gap: CTextView) {
        gap.background = ContextCompat.getDrawable(context, R.drawable.background_sentence_gap_selected_text)
    }

    fun getViewBottomLine(): View {
        val viewBottomLine = View(context)
        val params = LinearLayout.LayoutParams(MATCH_PARENT, context.resources.getDimension(R.dimen._1sdp).toInt())
     //   params.setMargins(0, context.resources.getDimension(R.dimen._1sdp).toInt(), 0, 0)
        viewBottomLine.layoutParams = params
        viewBottomLine.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
        return viewBottomLine
    }
}