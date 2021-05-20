package com.thirtyeight.thirtyeight.presentation.screens.mechanics.tinder

import android.view.View
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

/**
 * Created by nikolozakhvlediani on 3/19/21.
 */
abstract class AbsCardStackListener : CardStackListener {

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }
}