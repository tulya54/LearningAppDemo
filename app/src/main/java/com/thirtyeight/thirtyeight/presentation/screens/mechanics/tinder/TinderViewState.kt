package com.thirtyeight.thirtyeight.presentation.screens.mechanics.tinder

import com.thirtyeight.thirtyeight.domain.entities.mechanics.tinder.TinderItemEntity
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 3/14/21.
 */
data class TinderViewState(
        val list: List<TinderItemEntity>,
        val points: Int,
        val currentQuestionNumber: Int
) : ViewState()