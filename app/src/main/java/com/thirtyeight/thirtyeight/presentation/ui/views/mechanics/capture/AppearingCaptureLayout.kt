package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.capture

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.animation.ValueAnimator.REVERSE
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.*
import android.widget.FrameLayout
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.AppearingCaptureItemEntity
import com.thirtyeight.thirtyeight.presentation.ext.randomFloat
import kotlin.random.Random

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
class AppearingCaptureLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val viewPool = hashMapOf<AppearingCaptureItemEntity, CaptureItemLayout>()
    var onItemClicked: ((appearingCaptureItemEntity: AppearingCaptureItemEntity) -> Unit)? = null

    private var hashMap = hashMapOf<AppearingCaptureItemEntity, Coordinate>()

    private val matrix = mutableListOf<MutableList<Int>>()

    fun setContent(list: List<AppearingCaptureItemEntity>) {
        post {
            (0 until height).forEach { i ->
                val list = mutableListOf<Int>()
                (0 until width).forEach { j ->
                    list.add(0)
                }
                matrix.add(list)
            }
            /*val nElementsPerRow = width / 100.asPx
            val widthPerElement = width / nElementsPerRow
            val nElementsPerHeight = list.size / nElementsPerRow
            val heightPerElement = height / nElementsPerHeight*/
            list.forEachIndexed { index, appearingItemEntity ->
                /*val widthIndex = index % nElementsPerRow
                val heightIndex = index / nElementsPerRow
                val positionX = widthIndex * widthPerElement + widthPerElement / 2 - 25.asPx
                val positionY = heightIndex * heightPerElement + heightPerElement / 2 - 25.asPx*/
                val randomPosition = findRandomPosition()
                val positionX = randomPosition.x
                val positionY = randomPosition.y
                addToPosition(appearingItemEntity, positionX, positionY, true, false)
            }
        }
    }

    fun remove(appearingItemEntity: AppearingCaptureItemEntity) {
        viewPool[appearingItemEntity]?.let { view ->
            view.setOnClickListener { }
            ValueAnimator.ofFloat(1f, 0f).apply {
                duration = 200L
                addUpdateListener {
                    view.scaleX = it.animatedValue as Float
                    view.scaleY = it.animatedValue as Float
                }
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        removeView(view)
                    }
                })
                interpolator = AccelerateInterpolator()
                start()
            }
            viewPool.remove(appearingItemEntity)
            val sqiba = hashMap[appearingItemEntity]!!
            unBook(sqiba.x, sqiba.y)
        }
    }

    fun add(appearingItemEntity: AppearingCaptureItemEntity) {
        val randomPosition = findRandomPosition()
        val positionY = randomPosition.y
        val positionX = randomPosition.x
        addToPosition(
                appearingItemEntity,
                positionX,
                positionY,
                true
        )
    }

    fun setWrong(appearingCaptureItemEntity: AppearingCaptureItemEntity) {
        viewPool[appearingCaptureItemEntity]?.let {
            it.setWrong()
        }
    }

    private fun addToPosition(
            appearingItemEntity: AppearingCaptureItemEntity,
            positionX: Int,
            positionY: Int,
            animate: Boolean = false,
            delay: Boolean = true
    ) {
        val captureItemLayout = CaptureItemLayout(context).apply {
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                leftMargin = positionX
                topMargin = positionY
            }
            setContent(appearingItemEntity.image)
        }
        val anim = AnimationUtils.loadAnimation(context, R.anim.float_anim)
        val floatAnimation = createFloatAnimation(anim.interpolator)
        if (animate) {
            captureItemLayout.scaleX = 0f
            captureItemLayout.scaleY = 0f
        } else {
            captureItemLayout.setOnClickListener {
                onItemClicked?.invoke(appearingItemEntity)
            }
        }
        addView(captureItemLayout)
        if (animate) {
            ValueAnimator.ofFloat(0f, 1f).apply {
                duration = 200L
                addUpdateListener {
                    captureItemLayout.scaleX = it.animatedValue as Float
                    captureItemLayout.scaleY = it.animatedValue as Float
                }
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        captureItemLayout.setOnClickListener {
                            onItemClicked?.invoke(appearingItemEntity)
                        }
                    }
                })
                startDelay = if (delay) 200L else 0L
                interpolator = AccelerateInterpolator()
                start()
            }
        }
        captureItemLayout.startAnimation(floatAnimation)
        viewPool[appearingItemEntity] = captureItemLayout
        book(positionX, positionY)
        hashMap[appearingItemEntity] = Coordinate(positionX, positionY)
    }

    private fun unBook(positionX: Int, positionY: Int) {
        val startX = (positionX - ITEM_WIDTH).coerceAtLeast(0)
        val endX = (positionX + (ITEM_WIDTH)).coerceAtMost(matrix[0].size)
        val startY = (positionY - ONE_HALFT_ITEM_WIDTH).coerceAtLeast(0)
        val endY = (positionY + (ONE_HALFT_ITEM_WIDTH)).coerceAtMost(matrix.size)
        (startY until endY).forEach { y ->
            (startX until endX).forEach { x ->
                matrix[y][x]--
            }
        }
    }

    private fun book(positionX: Int, positionY: Int) {
        val startX = (positionX - ITEM_WIDTH).coerceAtLeast(0)
        val endX = (positionX + (ITEM_WIDTH)).coerceAtMost(matrix[0].size)
        val startY = (positionY - ONE_HALFT_ITEM_WIDTH).coerceAtLeast(0)
        val endY = (positionY + (ONE_HALFT_ITEM_WIDTH)).coerceAtMost(matrix.size)
        (startY until endY).forEach { y ->
            (startX until endX).forEach { x ->
                matrix[y][x]++
            }
        }
    }

    private fun findRandomPosition(): Coordinate {
        var randomX = Random.nextInt(0, matrix[0].size - ITEM_WIDTH)
        var randomY = Random.nextInt(ONE_HALFT_ITEM_WIDTH, matrix.size - ONE_HALFT_ITEM_WIDTH)
        while (matrix[randomY][randomX] != 0) {
            randomX = Random.nextInt(0, matrix[0].size - ITEM_WIDTH)
            randomY = Random.nextInt(ONE_HALFT_ITEM_WIDTH, matrix.size - ONE_HALFT_ITEM_WIDTH)
        }
        return Coordinate(randomX, randomY)
    }

    private fun createFloatAnimation(interpolator: Interpolator): TranslateAnimation {
        val delta = Random.randomFloat(20f, 40f)
        return TranslateAnimation(
                0f,
                0f,
                -delta,
                delta
        ).apply {
            repeatCount = Animation.INFINITE
            repeatMode = REVERSE
            this.interpolator = interpolator
            duration = Random.nextLong(800L, 1000L)
            fillAfter = true
            isFillEnabled = true
        }
    }

    private data class Coordinate(
            var x: Int,
            var y: Int
    )

    private val ITEM_WIDTH = 50.asPx
    private val ONE_HALFT_ITEM_WIDTH = 75.asPx
}