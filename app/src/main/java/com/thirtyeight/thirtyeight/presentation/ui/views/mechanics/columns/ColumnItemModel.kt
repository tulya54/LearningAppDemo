package com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.columns

import com.thirtyeight.thirtyeight.domain.entities.mechanics.columns.ColumnOptionEntity

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
data class ColumnItemModel(
        val columnOptionEntity: ColumnOptionEntity,
        val columnItemState: ColumnItemState
)