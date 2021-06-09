package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.bins

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.nikoloz14.myextensions.makeInvisible
import com.nikoloz14.myextensions.makeVisible
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinCategoryEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinFallingItemEntity
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.util.SpannableTools

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
abstract class BinsLayout<FIB : FallingItemBinLayout<*, BinData>, BinData> @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    FrameLayout(context, attrs, defStyleAttr) {

    abstract fun createFallingItemLayout(): FIB
    abstract val fallingDuration: Long
    abstract val title: String

    var categoryClicked: ((index: Int) -> Unit)? = null
    var itemReachedBottom: (() -> Unit)? = null

    private val fallingItemLayout: FIB
    private val frameLayout: FrameLayout
    private val columnsLinear: LinearLayout

    private var frameWidth = 0
    private var frameHeight = 0
    private var animation: ValueAnimator? = null

    private var binsColumns = mutableListOf<View>()
    private var binsCategories = mutableListOf<BinCategoryBoxLayout>()

    private var viewState = ViewState<BinData>(emptyList(), null, 0)
    private var previousViewState: ViewState<BinData> = viewState

    //  My change
    private var fallingContainers = mutableListOf<FrameLayout>()
    private var tabIndex = 0
    private var tabTotal = 0
    private var speedFalling: Long = 0
     var ivItem1: ImageView? = null
     var ivItem2: ImageView? = null
     var ivItem3: ImageView? = null
    private var ivTabBottomItem1: ImageView? = null
    private var ivTabBottomItem2: ImageView? = null
    private var ivTabBottomItem3: ImageView? = null
    private var clFrame1: ConstraintLayout? = null
    private var clFrame2: ConstraintLayout? = null
    private var btnReady: TextView? = null
    private var tvBug1: TextView? = null
    private var tvBug2: TextView? = null
    private var tvBug3: TextView? = null

    init {
        val view = context.inflateLayout(R.layout.layout_bins, this, true)
        frameLayout = view.findViewById(R.id.frame_layout)
        columnsLinear = view.findViewById(R.id.columns_linear)
        fallingItemLayout = createFallingItemLayout().apply {
            makeInvisible()
        }
        //  TEMPORARY WIDGETS
        ivItem1 = view.findViewById(R.id.ivItem1)
        ivItem2 = view.findViewById(R.id.ivItem2)
        ivItem3 = view.findViewById(R.id.ivItem3)
        ivTabBottomItem1 = view.findViewById(R.id.ivTabBottomItem1)
        ivTabBottomItem1?.visibility = View.VISIBLE
        ivTabBottomItem2 = view.findViewById(R.id.ivTabBottomItem2)
        ivTabBottomItem3 = view.findViewById(R.id.ivTabBottomItem3)
        speedFalling = fallingDuration
        clFrame1 = view.findViewById(R.id.clFrame1)
        clFrame2 = view.findViewById(R.id.clFrame2)
        btnReady = view.findViewById(R.id.btnReady)
        btnReady?.setOnClickListener {
            Toast.makeText(context, "Beta test", Toast.LENGTH_SHORT).show()
        }
        tvBug1 = view.findViewById(R.id.tvBug1)
        tvBug2 = view.findViewById(R.id.tvBug2)
        tvBug3 = view.findViewById(R.id.tvBug3)
    }

    fun setData(bins: List<BinCategoryEntity>, fallingItemEntity: BinFallingItemEntity<BinData>?,
                selectedBinIndex: Int) {
        invalidateViewState(
                viewState.copy(
                        bins = bins,
                        currentFallingItem = fallingItemEntity,
                        selectedBinIndex = selectedBinIndex
                )
        )
    }

    private fun invalidateViewState(viewState: ViewState<BinData>) {
        previousViewState = this.viewState.copy()
        this.viewState = viewState
        if (previousViewState.bins.isEmpty() && viewState.bins.isNotEmpty()) {
            setUp(viewState.bins)
        }
        if (viewState.currentFallingItem != previousViewState.currentFallingItem) {
            viewState.currentFallingItem?.let {
                with(fallingItemLayout) {
                    setData(it.data)
           //         translationX = binsColumns[viewState.selectedBinIndex].left.toFloat()
                    translationY = 0f
                    makeVisible()
                }
                startFalling()
            }
        }
        if (viewState.selectedBinIndex != previousViewState.selectedBinIndex) {
            animateX(viewState.selectedBinIndex)
        }
        binsCategories.forEachIndexed { index, binCategoryBoxLayout ->
            binCategoryBoxLayout.select(index == viewState.selectedBinIndex)
        }
    }

    /** ================================= */

    private fun setUp(bins: List<BinCategoryEntity>) {
        frameWidth = frameLayout.width - frameLayout.paddingStart - frameLayout.paddingEnd
        frameHeight = frameLayout.height
        //  My change
        columnsLinear.weightSum = bins.size.toFloat()
        tabTotal = bins.size
        bins.forEachIndexed { index, bin ->
            addColumn()
            if (index != bins.size - 1) {
                addSpaces()
            }
        }
        if (fallingContainers.size > 0) {
            val params = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            }
            fallingContainers[0].addView(fallingItemLayout, params)
        }
    }


    private fun addColumn() {
        val layoutParams = LinearLayout.LayoutParams(0, MATCH_PARENT, 1.0f)
        val view = FrameLayout(context)
       // view.background = ContextCompat.getDrawable(context, R.drawable.background_btn_ready_disable)
        columnsLinear.addView(view, layoutParams)
        binsColumns.add(view)
        fallingContainers.add(view)
    }

    private fun addSpaces() {
        val width = context.resources.getDimension(R.dimen._3sdp).toInt()
        val spaceLayoutParams = LinearLayout.LayoutParams(width, MATCH_PARENT)
        columnsLinear.addView(View(context).apply {
                     background = ContextCompat.getDrawable(context, R.drawable.vertical_line_gradient_drawable)
        }, spaceLayoutParams)
    }

    private fun startFalling() {
        val destination = frameHeight.toFloat() - fallingItemLayout.height
        ValueAnimator.ofFloat(fallingItemLayout.translationY, destination).apply {
            duration = speedFalling
            addUpdateListener {
                fallingItemLayout.translationY = it.animatedValue as Float
                duration = speedFalling
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    fallingItemLayout.makeInvisible()
                    itemReachedBottom?.invoke()
                }
            })
            start()
        }
    }

    private fun animateX(index: Int) {
        animation?.cancel()
        val destination = binsColumns[index].left
        animation = ValueAnimator.ofFloat(fallingItemLayout.translationX, destination.toFloat()).apply {
                    duration = 300L
                    addUpdateListener {
                   //     fallingItemLayout.translationX = it.animatedValue as Float
                    }
                    start()
                }
    }

    private data class ViewState<BinData>(
        val bins: List<BinCategoryEntity>,
        val currentFallingItem: BinFallingItemEntity<BinData>? = null,
        val selectedBinIndex: Int
    )

    fun moveFallingView(left: Boolean = false, right: Boolean = false): Boolean {
        speedFalling = 4000L
        if (fallingContainers.size > 0) {
            if (left && tabIndex > 0) {
                fallingContainers[tabIndex].removeView(fallingItemLayout)
                fallingContainers[tabIndex-1].addView(fallingItemLayout)
                tabIndex--
            } else if (right && tabIndex < tabTotal-1) {
                fallingContainers[tabIndex].removeView(fallingItemLayout)
                fallingContainers[tabIndex+1].addView(fallingItemLayout)
                tabIndex++
            }
        }
        ivTabBottomItem1?.let {
            it.visibility = if (tabIndex == 0) View.VISIBLE else View.GONE
        }
        ivTabBottomItem2?.let {
            it.visibility = if (tabIndex == 1) View.VISIBLE else View.GONE
        }
        ivTabBottomItem3?.let {
            it.visibility = if (tabIndex == 2) View.VISIBLE else View.GONE
        }
        if (left) return tabIndex == 0
        if (right) return tabIndex == 2
        return false
    }

    fun selectPosition(position: Int) {
        if (tabIndex == position) {
            return
        }
        speedFalling = 4000L
        if (fallingContainers.size > 0) {
            fallingContainers[tabIndex].removeView(fallingItemLayout)
            fallingContainers[position].addView(fallingItemLayout)
            tabIndex = position
        }
        ivTabBottomItem1?.let {
            it.visibility = if (tabIndex == 0) View.VISIBLE else View.GONE
        }
        ivTabBottomItem2?.let {
            it.visibility = if (tabIndex == 1) View.VISIBLE else View.GONE
        }
        ivTabBottomItem3?.let {
            it.visibility = if (tabIndex == 2) View.VISIBLE else View.GONE
        }
    }

    fun onResult(isGood: Boolean) {
        clFrame1?.visibility = View.GONE
        clFrame2?.visibility = View.VISIBLE
        if (isGood) {
            tvBug1?.text = SpannableTools.getSpannable(context, "26", "/26", R.color.green_dark)
            tvBug2?.text = SpannableTools.getSpannable(context, "26", "/26", R.color.green_dark)
            tvBug3?.text = SpannableTools.getSpannable(context, "26", "/26", R.color.green_dark)
            btnReady?.background = ContextCompat.getDrawable(context, R.drawable.background_btn_green_dark)
        } else {
            tvBug1?.text = SpannableTools.getSpannable(context, "26", "/26", R.color.red_dark)
            tvBug2?.text = SpannableTools.getSpannable(context, "26", "/26", R.color.red_dark)
            tvBug3?.text = SpannableTools.getSpannable(context, "26", "/26", R.color.red_dark)
            btnReady?.background = ContextCompat.getDrawable(context, R.drawable.background_btn_red_dark)
        }
    }
}