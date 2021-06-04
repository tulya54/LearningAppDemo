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
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.nikoloz14.myextensions.asPx
import com.nikoloz14.myextensions.makeInvisible
import com.nikoloz14.myextensions.makeVisible
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinCategoryEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinFallingItemEntity
import com.thirtyeight.thirtyeight.presentation.ext.getColorFromAttr
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
abstract class BinsLayout<FIB : FallingItemBinLayout<*, BinData>, BinData> @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    abstract fun createFallingItemLayout(): FIB
    abstract val fallingDuration: Long
    abstract val title: String

    var categoryClicked: ((index: Int) -> Unit)? = null
    var itemReachedBottom: (() -> Unit)? = null

    //private val titleTextView: TextView
    private val fallingItemLayout: FIB
    private val frameLayout: FrameLayout
    private val columnsLinear: LinearLayout
    private val categoriesLinear: LinearLayout

    private var frameWidth = 0
    private var frameHeight = 0
    private var animation: ValueAnimator? = null

    private var binsColumns = mutableListOf<View>()
    private var binsCategories = mutableListOf<BinCategoryBoxLayout>()

    private var viewState = ViewState<BinData>(emptyList(), null, 0)
    private var previousViewState: ViewState<BinData> = viewState

    init {
        val view = context.inflateLayout(R.layout.layout_bins, this, true)
    //    titleTextView = view.findViewById(R.id.title_text_view)//title_text_view
        frameLayout = view.findViewById(R.id.frame_layout)
        columnsLinear = view.findViewById(R.id.columns_linear)
        categoriesLinear = view.findViewById(R.id.categories_linear)
        fallingItemLayout = createFallingItemLayout().apply {
            makeInvisible()
        }
        frameLayout.addView(
                fallingItemLayout,
                LayoutParams(
                        100.asPx, 40.asPx
                )
        )
       // titleTextView.text = title
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
                    translationX = binsColumns[viewState.selectedBinIndex].left.toFloat()
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
        bins.forEachIndexed { index, bin ->
            addColumn()
            addCategory(bin, index)
            if (index != bins.size - 1) {
                addSpaces()
            }
        }
    }

    private fun addColumn() {
        val layoutParams = LinearLayout.LayoutParams(100.asPx, MATCH_PARENT)
        val view = View(context).apply {
            setBackgroundColor(context.getColorFromAttr(R.attr.binColumnColor))
        }
        columnsLinear.addView(view, layoutParams)
        binsColumns.add(view)
    }

    private fun addCategory(binCategoryEntity: BinCategoryEntity, index: Int) {
        val categoryLayoutParams = LinearLayout.LayoutParams(100.asPx, WRAP_CONTENT)
        val binCategoryLayout = BinCategoryBoxLayout(context).apply {
            setText(binCategoryEntity.text)
            setOnClickListener {
                categoryClicked?.invoke(index)
            }
        }
        categoriesLinear.addView(binCategoryLayout, categoryLayoutParams.apply {
            gravity = Gravity.BOTTOM
        })
        binsCategories.add(binCategoryLayout)
    }

    private fun addSpaces() {
        val spaceLayoutParams = LinearLayout.LayoutParams(0.asPx, MATCH_PARENT).apply {
                    weight = 1f
                }
        columnsLinear.addView(View(context), spaceLayoutParams)

        val categorySpaceLayoutParams = LinearLayout.LayoutParams(0.asPx, WRAP_CONTENT).apply {
                    weight = 1f
                }
        categoriesLinear.addView(View(context), categorySpaceLayoutParams.apply {
            gravity = Gravity.BOTTOM
        })
    }

    private fun startFalling() {
        val destination = frameHeight.toFloat() - fallingItemLayout.height
        ValueAnimator.ofFloat(fallingItemLayout.translationY, destination).apply {
            duration = fallingDuration
            addUpdateListener {
                fallingItemLayout.translationY = it.animatedValue as Float
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
                        fallingItemLayout.translationX = it.animatedValue as Float
                    }
                    start()
                }
    }

    private data class ViewState<BinData>(
        val bins: List<BinCategoryEntity>,
        val currentFallingItem: BinFallingItemEntity<BinData>? = null,
        val selectedBinIndex: Int
    )
}