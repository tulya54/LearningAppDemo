package com.thirtyeight.thirtyeight.presentation.screens.mechanicsession

import com.thirtyeight.thirtyeight.domain.entities.mechanics.MechanicDataEntity

/**
 * Created by nikolozakhvlediani on 4/9/21.
 */
sealed class MechanicSessionWish {

    class DataLoaded(val mechanics: List<MechanicDataEntity>) : MechanicSessionWish()
    object LoadNext : MechanicSessionWish()
    class ShowResult(val points: Int, val from: Int, val showNext: Boolean) : MechanicSessionWish()
    object Retry : MechanicSessionWish()
}