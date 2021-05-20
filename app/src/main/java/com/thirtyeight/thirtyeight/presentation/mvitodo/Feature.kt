package com.thirtyeight.thirtyeight.presentation.mvitodo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
// TODO
interface Feature<Wish : Any, State : Any, Event : Any> {

    @OptIn(ExperimentalCoroutinesApi::class)
    val eventsChannel: BroadcastChannel<Event>
}