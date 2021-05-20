package com.thirtyeight.thirtyeight.util.drag

import android.view.DragEvent
import android.view.View

/**
 * Callback for the target view where dragged items will be dropped
 * */
class DropListener(private val onDrop: () -> Unit) : View.OnDragListener {
    override fun onDrag(view: View, dragEvent: DragEvent): Boolean {
        when (dragEvent.action) {
            DragEvent.ACTION_DROP -> {
                onDrop()
            }
        }

        return true
    }
}
