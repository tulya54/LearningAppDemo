package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.nex3z.flowlayout.FlowLayout
import com.nikoloz14.myextensions.isVisible
import com.nikoloz14.myextensions.makeInvisible
import com.nikoloz14.myextensions.makeVisible
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapOptionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.GapQuestionEntity
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.util.drag.DragListener

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
abstract class GapLayout<GapView : View, GapData, OptionData> @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var optionChosen: ((optionId: Long, gapIndex: Int) -> Unit)? = null
    var gapClicked: ((gapIndex: Int) -> Unit)? = null

    // TODO note protected
    protected val gapContainer: FrameLayout
    private val flowLayout: FlowLayout

    private var gapQuestionEntity: GapQuestionEntity<GapData, OptionData>? = null

    // TODO not protected
    protected val gaps: MutableList<GapView> = mutableListOf()
    protected val availableGaps: MutableList<Boolean> = mutableListOf()
    protected val viewPool = hashMapOf<Long, GapView>()
    protected val gapPool = hashMapOf<GapView, Long>()
    protected val optionData = hashMapOf<GapView, GapOptionEntity<OptionData>>()

    protected var draggedItem: GapView? = null

    abstract fun addGaps(gapData: GapData, clickListener: (gapView: GapView, index: Int) -> Unit)
    abstract fun createGapView(): GapView
    abstract fun clearGap(gap: GapView)
    abstract fun setDataToGap(gap: GapView, data: OptionData)
    abstract val gapDimensions: Dimensions

    init {
        val view = context.inflateLayout(R.layout.layout_gap, this, true)
        flowLayout = view.findViewById(R.id.flow_layout)
        gapContainer = view.findViewById(R.id.gap_container)
    }

    fun setQuestion(gapQuestionEntity: GapQuestionEntity<GapData, OptionData>) {
        if (this.gapQuestionEntity == null) {
            addOptions(gapQuestionEntity)
            addGaps(gapQuestionEntity.gapData) { gapView, index ->
                clearGap(gapView)
                availableGaps[index] = true
                viewPool[gapPool[gapView]]?.makeVisible()
            }
            this.gapQuestionEntity = gapQuestionEntity
        }
    }

    private fun addOptions(gapQuestionEntity: GapQuestionEntity<GapData, OptionData>) {
        gapQuestionEntity.options.forEach { option ->
            val optionView = createGapView()
            optionData[optionView] = option
            setDataToGap(optionView, option.data)
            viewPool[option.id] = optionView
            optionView.setOnClickListener {
                if (it.isVisible()) {
                    val firstGapIndex = availableGaps.indexOfFirst { it }
                    if (firstGapIndex != -1) {
                        chooseOptionForGap(firstGapIndex, option, optionView)
                    }
                }
            }
            optionView.setOnLongClickListener { view ->
                // when user is long clicking on a view, drag process will start
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(view)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    view.startDragAndDrop(data, shadowBuilder, view, 0)
                } else {
                    view.startDrag(data, shadowBuilder, view, 0)
                }
                draggedItem = optionView
                true
            }
            setOnDragListener(DragListener())
            flowLayout.addView(
                    optionView,
                    LinearLayout.LayoutParams(gapDimensions.width, gapDimensions.height)
            )
        }
    }

    protected fun chooseOptionForGap(
            gapIndex: Int,
            option: GapOptionEntity<OptionData>,
            gapView: GapView
    ) {
        optionChosen?.invoke(option.id, gapIndex)
        val gap = gaps[gapIndex]
        setDataToGap(gap, option.data)
        availableGaps[gapIndex] = false
        gapPool[gap] = option.id
        gapView.setOnDragListener(null)
        viewPool[option.id]?.makeInvisible()
    }

    data class Dimensions(
            val width: Int,
            val height: Int
    )
}