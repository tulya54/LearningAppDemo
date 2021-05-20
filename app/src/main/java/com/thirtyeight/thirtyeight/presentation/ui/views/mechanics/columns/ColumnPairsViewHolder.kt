package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.columns

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nikoloz14.myextensions.makeVisibleOrInvisible
import com.thirtyeight.thirtyeight.R

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ColumnPairsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val firstColumnItemLayout =
            itemView.findViewById<ColumnItemLayout>(R.id.first_column_item_layout)
    private val secondColumnItemLayout =
            itemView.findViewById<ColumnItemLayout>(R.id.second_column_item_layout)
    private val arrow = itemView.findViewById<View>(R.id.arrow)

    fun setContent(pair: Pair<ColumnItemModel, ColumnItemModel>, onItemClick: ((Long) -> Unit)?) {
        firstColumnItemLayout.setText(pair.first.columnOptionEntity.text)
        firstColumnItemLayout.setOnClickListener {
            onItemClick?.invoke(pair.first.columnOptionEntity.id)
        }
        secondColumnItemLayout.setText(pair.second.columnOptionEntity.text)
        secondColumnItemLayout.setOnClickListener {
            onItemClick?.invoke(pair.second.columnOptionEntity.id)
        }
        update(pair)
    }

    fun update(pair: Pair<ColumnItemModel, ColumnItemModel>) {
        val disabled = pair.first.columnItemState == ColumnItemState.DISABLED ||
                pair.second.columnItemState == ColumnItemState.DISABLED
        arrow.makeVisibleOrInvisible(disabled)
        firstColumnItemLayout.setState(pair.first.columnItemState)
        secondColumnItemLayout.setState(pair.second.columnItemState)
    }
}