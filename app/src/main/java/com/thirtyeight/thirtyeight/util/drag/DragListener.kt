package com.thirtyeight.thirtyeight.util.drag

import android.view.DragEvent
import android.view.View

/**
 * Callback for the draggable view to update it's visibility
 * */
class DragListener : View.OnDragListener {
    override fun onDrag(view: View, dragEvent: DragEvent): Boolean {
        return true
    }
}
