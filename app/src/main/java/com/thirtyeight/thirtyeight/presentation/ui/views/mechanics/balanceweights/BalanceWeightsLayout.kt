package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.balanceweights

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 4/3/21.
 */
class BalanceWeightsLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageViewOne: ImageView
    private val imageViewTwo: ImageView
    private val weightOneTextView: TextView
    private val weightTwoTextView: TextView
    private val rod: View

    private var viewState = ViewState(
            WeightItemModel(0, "", 0),
            WeightItemModel(0, "", 0),
            0
    )
    private var previousViewState: ViewState = viewState

    init {
        val view = context.inflateLayout(R.layout.layout_balance_weights, this, true)
        imageViewOne = view.findViewById(R.id.image_view_one)
        imageViewTwo = view.findViewById(R.id.image_view_two)
        rod = view.findViewById(R.id.rod)
        weightOneTextView = view.findViewById(R.id.weight_one_text_view)
        weightTwoTextView = view.findViewById(R.id.weight_two_text_view)
    }

    fun setData(itemOne: WeightItemModel, itemTwo: WeightItemModel, initialWeightRight: Int) {
        viewState = viewState.copy(
                itemOne = itemOne,
                itemTwo = itemTwo,
                initialWeightRight = initialWeightRight
        )
        invalidateViewState()
    }

    var animation: ValueAnimator? = null
    private fun invalidateViewState() {
        with(viewState) {
            if (viewState.itemOne.imageRes != previousViewState.itemOne.imageRes) {
                imageViewOne.setImageResource(viewState.itemOne.imageRes)
            }
            if (viewState.itemTwo.imageRes != previousViewState.itemTwo.imageRes) {
                imageViewTwo.setImageResource(viewState.itemTwo.imageRes)
            }
            weightOneTextView.text = viewState.itemOne.text
            weightTwoTextView.text = viewState.itemTwo.text
            previousViewState = this
            if (itemOne.weight != 0) {
                animation?.cancel()
                val offset = (itemOne.weight - initialWeightRight) / -12f
                val rotationDest = ((itemOne.weight - itemTwo.weight) / offset).coerceAtLeast(-12f)
                ValueAnimator.ofFloat(rod.rotation, rotationDest).apply {
                    duration = 500L
                    addUpdateListener {
                        rod.rotation = it.animatedValue as Float
                        rod.rotation = it.animatedValue as Float
                    }
                    interpolator = BounceInterpolator()
                    start()
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        animation?.cancel()
        super.onDetachedFromWindow()
    }

    data class ViewState(
            val itemOne: WeightItemModel,
            val itemTwo: WeightItemModel,
            val initialWeightRight: Int
    )

    data class WeightItemModel(
            val weight: Int,
            val text: String,
            val imageRes: Int
    )
}