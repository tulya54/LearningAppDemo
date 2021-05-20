package com.thirtyeight.thirtyeight.presentation.screens.mechanicsession

import com.thirtyeight.thirtyeight.domain.entities.mechanics.MechanicDataEntity
import com.thirtyeight.thirtyeight.presentation.logic.ViewState

/**
 * Created by nikolozakhvlediani on 4/9/21.
 */
data class MechanicSessionViewState(
        val title: String,
        val mechanicSessionData: List<MechanicDataEntity>,
        val currentStep: MechanicDataEntity?,
        val currentIndex: Int,
        val resultShown: Boolean,
        val points: Int,
        val from: Int,
        val progress: Float,
        val showNext: Boolean
) : ViewState()