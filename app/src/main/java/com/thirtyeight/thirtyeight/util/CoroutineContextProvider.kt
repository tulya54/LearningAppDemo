package com.thirtyeight.thirtyeight.util

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
interface CoroutineContextProvider {
    val main: CoroutineContext
    val iO: CoroutineContext
    val default: CoroutineContext
}

open class DefaultCoroutineContextProvider : CoroutineContextProvider {
    override val main: CoroutineContext by lazy { Dispatchers.Main }
    override val iO: CoroutineContext by lazy { Dispatchers.IO }
    override val default: CoroutineContext by lazy { Dispatchers.Default }
}