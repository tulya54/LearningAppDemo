package com.thirtyeight.thirtyeight.presentation.mvitodo

import kotlinx.coroutines.channels.BroadcastChannel

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
// TODO
class CounterFeature :
        ReducerFeature<CounterFeature.Wish, CounterFeature.State, CounterFeature.Event>(
                initialState = State(),
                reducer = ReducerImpl()
        ) {

    override val eventsChannel = BroadcastChannel<Event>(1)

    data class State(
            val counter: Int = 0
    )

    sealed class Wish {
        object IncrementCounter : Wish()
    }

    sealed class Event {
        object Close : Event()
    }

    private class ReducerImpl : Reducer<State, Wish> {
        override fun invoke(state: State, wish: Wish): State =
                when (wish) {
                    Wish.IncrementCounter -> state.copy(
                            counter = state.counter + 1
                    )
                }
    }
}