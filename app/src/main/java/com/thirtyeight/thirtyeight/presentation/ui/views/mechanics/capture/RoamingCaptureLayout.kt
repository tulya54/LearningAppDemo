package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.capture

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.domain.entities.mechanics.capture.RoamingCaptureItemEntity
import kotlin.math.abs
import kotlin.random.Random

/**
 * Created by nikolozakhvlediani on 3/21/21.
 */
class RoamingCaptureLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val objects = mutableListOf<Item>()
    private val viewPool = hashMapOf<Long, CaptureItemLayout>()
    private val set = mutableSetOf<Pair<Long, Long>>()

    private var movementHandler: Handler? = null
    private var movementRunnable: Runnable? = null

    private val slots = mutableListOf<Slot>()
    private val waiting = mutableListOf<RoamingCaptureItemEntity>()

    var onItemClicked: ((roamingCaptureEntity: RoamingCaptureItemEntity) -> Unit)? = null

    fun addData(list: List<RoamingCaptureItemEntity>) {
        post {
            val nElementsPerRow = width / 100.asPx
            val widthPerElement = width / nElementsPerRow
            val nElementsPerHeight = list.size / nElementsPerRow
            val heightPerElement =
                    if (nElementsPerHeight == 0) height else height / nElementsPerHeight
            list.forEachIndexed { index, roamingCaptureEntity ->
                val widthIndex = index % nElementsPerRow
                val heightIndex = index / nElementsPerRow
                val positionX = widthIndex * widthPerElement + widthPerElement / 2 - 25.asPx
                val positionY = heightIndex * heightPerElement + heightPerElement / 2 - 25.asPx
                val centerCoordinate = Coordinate(positionX + 25.asPx, positionY + 30.asPx)
                val slot = Slot(roamingCaptureEntity.id, centerCoordinate, Movement(25, 0), false)
                createItemAndView(
                        centerCoordinate,
                        roamingCaptureEntity,
                        slot,
                        animate = true
                )
                slots.add(slot)
            }
            startMovement()
        }
    }

    fun remove(roamingCaptureEntity: RoamingCaptureItemEntity) {
        viewPool[roamingCaptureEntity.id]?.let { view ->
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
            viewPool.remove(roamingCaptureEntity)
            objects.firstOrNull { it.id == roamingCaptureEntity.id }?.let {
                it.slot.available = true
            }
            objects.remove(objects.first { it.id == roamingCaptureEntity.id })
            if (waiting.isNotEmpty())
                add(waiting[0])
        }
    }

    fun add(roamingCaptureEntity: RoamingCaptureItemEntity) {
        slots.filter { it.available }.shuffled().getOrNull(0)?.let {
            createItemAndView(
                    it.centerCoordinate,
                    roamingCaptureEntity,
                    it.apply {
                        available = false
                    },
                    animate = true,
                    delay = true,
                    movement = it.movement
            )
        } ?: run {
            waiting.add(roamingCaptureEntity)
        }
    }

    fun setWrong(roamingCaptureEntity: RoamingCaptureItemEntity) {
        viewPool[roamingCaptureEntity.id]?.let {
            it.setWrong()
        }
    }

    private fun updatePosition(captureItemLayout: CaptureItemLayout, item: Item) {
        val layoutParams = captureItemLayout.layoutParams as LayoutParams
        layoutParams.apply {
            leftMargin = item.rect.left
            topMargin = item.rect.top
        }
        captureItemLayout.layoutParams = layoutParams
        item.slot.centerCoordinate.apply {
            x = item.center.x
            y = item.center.y
        }
        item.slot.movement = item.movement
    }

    private fun startMovement() {
        movementRunnable?.let {
            movementHandler?.post(it)
        }
    }

    private fun checkIntersections() {
        for (i in 0 until objects.size) {
            val obj1 = objects[i]
            for (j in i + 1 until objects.size) {
                val obj2 = objects[j]
                if (Rect.intersects(obj1.rect, obj2.rect)) {
                    if (!set.contains(obj1.id to obj2.id)) {
                        set.add(obj1.id to obj2.id)
                        var invertY = false
                        var invertX = false
                        if (obj1.movement.dx == obj2.movement.dx) {
                            invertY = true
                        } else {
                            invertX = true
                        }
                        if (obj1.movement.dy == obj2.movement.dy) {
                            invertX = true
                        } else {
                            invertY = true
                        }
                        if (invertX) {
                            obj1.movement.dx = -obj1.movement.dx
                            obj2.movement.dx = -obj2.movement.dx
                        }
                        if (invertY) {
                            obj1.movement.dy = -obj1.movement.dy
                            obj2.movement.dy = -obj2.movement.dy
                        }
                        if (obj1.movement.dx == obj2.movement.dx && obj1.movement.dy == obj2.movement.dy) {
                            obj1.movement.dx = -obj1.movement.dx
                            obj2.movement.dx = -obj2.movement.dx
                            obj1.movement.dy = -obj1.movement.dy
                            obj2.movement.dy = -obj2.movement.dy
                        }
                    }
                } else {
                    set.remove(obj1.id to obj2.id)
                }
            }
        }
    }

    private fun updateObjectPosition(item: Item) {
        item.rect.left = item.rect.left + item.movement.dx
        item.rect.top = item.rect.top + item.movement.dy
        item.rect.right = item.rect.right + item.movement.dx
        item.rect.bottom = item.rect.bottom + item.movement.dy
        item.center.x = item.center.x + item.movement.dx
        item.center.y = item.center.y + item.movement.dy
    }

    private fun checkWallCollision(item: Item) {
        if (item.rect.right >= width) {
            item.movement.dx = -abs(item.movement.dx)
        } else if (item.rect.left <= 0) {
            item.movement.dx = abs(item.movement.dx)
        }
        if (item.rect.bottom >= height) {
            item.movement.dy = -abs(item.movement.dy)
        } else if (item.rect.top <= 0) {
            item.movement.dy = abs(item.movement.dy)
        }
    }

    private fun getRandom(): Int {
        var value = Random.nextInt(0, 2)
        if (value == 0)
            value = -1
        return value * 6
    }

    private fun createItemAndView(
            center: Coordinate,
            roamingCaptureEntity: RoamingCaptureItemEntity,
            slot: Slot,
            animate: Boolean = false,
            delay: Boolean = true,
            movement: Movement = Movement(getRandom(), getRandom())
    ) {
        val obj = Item(
                roamingCaptureEntity.id,
                center,
                Rect(
                        center.x - 25.asPx,
                        center.y - 25.asPx,
                        center.x + 25.asPx,
                        center.y + 25.asPx
                ),
                movement,
                slot.apply {
                    this.movement = movement
                }
        )
        val captureItemLayout = CaptureItemLayout(context).apply {
            layoutParams = LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                leftMargin = obj.rect.left
                topMargin = obj.rect.top
            }
            setContent(roamingCaptureEntity.image)
        }
        if (animate) {
            captureItemLayout.scaleX = 0f
            captureItemLayout.scaleY = 0f
        } else {
            captureItemLayout.setOnClickListener {
                onItemClicked?.invoke(roamingCaptureEntity)
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
                            onItemClicked?.invoke(roamingCaptureEntity)
                        }
                    }
                })
                startDelay = if (delay) 200L else 0L
                interpolator = AccelerateInterpolator()
                start()
            }
        }
        viewPool[obj.id] = captureItemLayout
        objects.add(obj)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        movementHandler = Handler(Looper.getMainLooper())
        movementRunnable = Runnable {
            checkIntersections()
            objects.forEach {
                checkWallCollision(it)
            }
            objects.forEach {
                updateObjectPosition(it)
            }
            objects.forEach {
                updatePosition(viewPool[it.id]!!, it)
            }
            movementHandler?.postDelayed(movementRunnable!!, 30L)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        movementRunnable?.let {
            movementHandler?.removeCallbacks(it)
        }
        movementRunnable = null
    }

    private data class Item(
            val id: Long,
            var center: Coordinate,
            var rect: Rect,
            var movement: Movement,
            val slot: Slot
    )

    private data class Coordinate(
            var x: Int,
            var y: Int
    )

    private data class Movement(
            var dx: Int,
            var dy: Int
    )

    private data class Slot(
            val id: Long,
            var centerCoordinate: Coordinate,
            var movement: Movement,
            var available: Boolean
    )
}