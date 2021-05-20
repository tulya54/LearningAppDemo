package com.thirtyeight.thirtyeight.domain.valueobjects

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
data class Coordinate(
        var row: Int = 0,
        var col: Int = 0
) : Serializable