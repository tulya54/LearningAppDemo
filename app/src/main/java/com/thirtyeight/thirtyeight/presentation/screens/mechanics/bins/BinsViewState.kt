package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins

import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinCategoryEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinFallingItemEntity
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
data class BinsViewState<BinData>(
        val bins: List<BinCategoryEntity>,
        val currentFallingItem: BinFallingItemEntity<BinData>? = null,
        val fallingItems: List<BinFallingItemEntity<BinData>>,
        val selectedBinIndex: Int,
        val points: Int,
        val from: Int
) : ViewState()