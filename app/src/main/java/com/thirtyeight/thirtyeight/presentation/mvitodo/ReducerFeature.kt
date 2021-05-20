package com.thirtyeight.thirtyeight.presentation.mvitodo

/**
 * Created by nikolozakhvlediani on 3/25/21.
 */
// TODO
typealias Reducer<State, Wish> = (State, Wish) -> State

abstract class ReducerFeature<Wish : Any, State : Any, Event : Any>(
        val initialState: State,
        val reducer: Reducer<State, Wish>
) : Feature<Wish, Any, Event>