package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.columns

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikoloz14.myextensions.asPx
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.columns.ColumnOptionEntity
import com.thirtyeight.thirtyeight.presentation.ext.inflateLayout
import com.thirtyeight.thirtyeight.util.VerticalSpaceItemDecoration

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class ColumnsLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var onItemClick: ((Long) -> Unit)? = null

    private lateinit var adapter: ColumnsListAdapter

    private val recyclerView: RecyclerView

    private var viewState = ViewState(emptyList(), emptyList(), emptyList(), null)
    private var previousViewState: ViewState = viewState

    init {
        val view = context.inflateLayout(R.layout.layout_columns, this, true)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = VerticalSpaceItemDecoration(10.asPx)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun invalidateViewState() {
        with(viewState) {
            val newList = generateListFromData(columnOne, columnTwo, selectedPairs, selectedItem)
            if (!::adapter.isInitialized) {
                adapter = ColumnsListAdapter(onItemClick)
                recyclerView.adapter = adapter
            }
            adapter.updateList(newList)
            previousViewState = this
        }
    }

    fun setData(
            columnOne: List<ColumnOptionEntity>,
            columnTwo: List<ColumnOptionEntity>,
            selectedPairs: List<Pair<Long, Long>>,
            selectedItem: Long?
    ) {
        viewState = viewState.copy(
                columnOne = columnOne,
                columnTwo = columnTwo,
                selectedPairs = selectedPairs,
                selectedItem = selectedItem
        )
        invalidateViewState()
    }

    fun generateListFromData(
            columnOne: List<ColumnOptionEntity>,
            columnTwo: List<ColumnOptionEntity>,
            selectedPairs: List<Pair<Long, Long>>,
            selectedItem: Long?
    ): MutableList<Pair<ColumnItemModel, ColumnItemModel>> {
        val result = mutableListOf<Pair<ColumnItemModel, ColumnItemModel>>()
        val mutableColumnOne = columnOne.toMutableList()
        val mutableColumnTwo = columnTwo.toMutableList()
        selectedPairs.forEach { pair ->
            result.add(
                    ColumnItemModel(
                            (columnOne.first { it.id == pair.first }),
                            ColumnItemState.DISABLED
                    ) to ColumnItemModel(
                            (columnTwo.first { it.id == pair.second }),
                            ColumnItemState.DISABLED
                    )
            )
            mutableColumnOne.removeAll { it.id == pair.first }
            mutableColumnTwo.removeAll { it.id == pair.second }
        }
        (0 until mutableColumnOne.size).forEach { index ->
            val item1 = mutableColumnOne[index]
            val item2 = mutableColumnTwo[index]
            result.add(
                    ColumnItemModel(
                            item1,
                            if (item1.id == selectedItem)
                                ColumnItemState.SELECTED
                            else
                                ColumnItemState.NONE
                    ) to ColumnItemModel(
                            item2,
                            if (item2.id == selectedItem)
                                ColumnItemState.SELECTED
                            else
                                ColumnItemState.NONE
                    )
            )
        }
        return result
    }

    private data class ViewState(
            val columnOne: List<ColumnOptionEntity>,
            val columnTwo: List<ColumnOptionEntity>,
            val selectedPairs: List<Pair<Long, Long>>,
            val selectedItem: Long?
    )
}