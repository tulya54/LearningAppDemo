package com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.order

import com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.order.OrderGapData
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.gap.GapFragment
import com.thirtyeight.thirtyeight.presentation.ui.views.mechanics.gap.order.OrderGapLayout

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
abstract class OrderGapFragment<OrderGapOptionData, VM : OrderGapViewModel<OrderGapOptionData>, OGL : OrderGapLayout<OrderGapOptionData, *>> :
        GapFragment<OrderGapData, OrderGapOptionData, VM, OGL>()