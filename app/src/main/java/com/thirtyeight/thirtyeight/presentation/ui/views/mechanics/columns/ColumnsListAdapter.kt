package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.columns

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ColumnsListAdapter(
        private val onItemClick: ((Long) -> Unit)?
) : RecyclerView.Adapter<ColumnPairsViewHolder>() {

    private val list: MutableList<Pair<ColumnItemModel, ColumnItemModel>> = mutableListOf()

    fun updateList(newList: List<Pair<ColumnItemModel, ColumnItemModel>>) {
        val diffResult = DiffUtil.calculateDiff(ColumnItemsDiffCallback(this.list, newList), true)
        diffResult.dispatchUpdatesTo(this)
        list.clear()
        list.addAll(newList)
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColumnPairsViewHolder =
            ColumnPairsViewHolder(
                    parent.context.inflateLayout(
                            R.layout.view_holder_column_pairs,
                            parent
                    )
            )

    override fun onBindViewHolder(holder: ColumnPairsViewHolder, position: Int) {
        holder.setContent(list[position], onItemClick)
    }

    override fun onBindViewHolder(
            holder: ColumnPairsViewHolder,
            position: Int,
            payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.update(payloads[0] as Pair<ColumnItemModel, ColumnItemModel>)
        }
    }
}