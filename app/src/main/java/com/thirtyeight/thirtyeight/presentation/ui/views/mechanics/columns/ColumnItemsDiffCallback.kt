package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.columns

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ColumnItemsDiffCallback(
        private val oldList: List<Pair<ColumnItemModel, ColumnItemModel>>,
        private val newList: List<Pair<ColumnItemModel, ColumnItemModel>>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].first.columnOptionEntity.id == newList[newItemPosition].first.columnOptionEntity.id &&
                    oldList[oldItemPosition].second.columnOptionEntity.id == newList[newItemPosition].second.columnOptionEntity.id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val state1 = oldList[oldItemPosition].first.columnItemState
        val state2 = oldList[oldItemPosition].second.columnItemState
        val state3 = newList[newItemPosition].first.columnItemState
        val state4 = newList[newItemPosition].second.columnItemState
        return if (state1 == state3 && state2 == state4)
            super.getChangePayload(oldItemPosition, newItemPosition)
        else
            newList[newItemPosition]
    }
}