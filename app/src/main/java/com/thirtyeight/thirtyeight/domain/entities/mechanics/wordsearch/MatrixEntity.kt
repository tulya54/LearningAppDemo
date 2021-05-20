package com.thirtyeight.thirtyeight.domain.entities.mechanics.wordsearch

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
data class MatrixEntity(
        val matrix: Array<Array<Char>>
) : Serializable {
    private val rowCount: Int
        get() = matrix.size

    private val colCount: Int
        get() = matrix.getOrNull(0)?.size ?: 0

    private fun at(row: Int, col: Int): Char = matrix[row][col]

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (i in 0 until rowCount) {
            for (j in 0 until colCount) {
                stringBuilder.append(at(i, j))
            }
            if (i != rowCount - 1) stringBuilder.append('\n')
        }
        return stringBuilder.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MatrixEntity

        if (!matrix.contentDeepEquals(other.matrix)) return false

        return true
    }

    override fun hashCode(): Int {
        return matrix.contentDeepHashCode()
    }
}