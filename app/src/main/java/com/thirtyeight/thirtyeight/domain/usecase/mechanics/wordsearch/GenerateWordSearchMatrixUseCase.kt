package com.thirtyeight.thirtyeight.domain.usecase.mechanics.wordsearch

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.wordsearch.MatrixEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.wordsearch.WordSearchDataEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.domain.valueobjects.Direction
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

/**
 * Created by nikolozakhvlediani on 4/4/21.
 */
class GenerateWordSearchMatrixUseCase :
        BaseUseCase<GenerateWordSearchMatrixUseCase.Input, WordSearchDataEntity>() {

    override fun execute(input: Input): WordSearchDataEntity {
        val matrix = Array(input.rowCount) {
            Array(input.columnCount) {
                EMPTY_CHAR
            }
        }
        val words = listOf("DOG", "CAT", "MOUSE")
        generateStuff(words, matrix)
        return WordSearchDataEntity(
                R.string.find_required_words_among_characters,
                MatrixEntity(matrix),
                words
        )
    }

    private fun generateStuff(words: List<String>, matrix: Array<Array<Char>>) {
        val usedWords: MutableList<String> = ArrayList()
        resetMatrix(matrix)
        var usedCount = 0
        for (word in words) {
            if (tryPlaceWord(word, matrix)) {
                usedCount++
                usedWords.add(word)
            }
        }

        fillRestWithRandom(matrix)
    }

    private fun resetMatrix(matrix: Array<Array<Char>>) {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                matrix[i][j] = EMPTY_CHAR
            }
        }
    }

    private fun fillRestWithRandom(matrix: Array<Array<Char>>) {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == EMPTY_CHAR) matrix[i][j] = getRandomChar()
            }
        }
    }

    private fun getRandomChar(): Char {
        return (65 + abs(Random().nextInt()) % (90 - 66)).toChar()
    }

    private fun getRandomInt(): Int {
        return abs(Random().nextInt())
    }

    private fun getRandomDirection(): Direction {
        var dir: Direction
        val possibleDirections = listOf(Direction.EAST, Direction.SOUTH)
        dir = possibleDirections[getRandomInt() % possibleDirections.size]
        return dir
    }

    private fun tryPlaceWord(word: String, matrix: Array<Array<Char>>): Boolean {
        val startDir = getRandomDirection()
        var currDir = startDir
        var row: Int
        var col: Int
        var startRow: Int
        var startCol: Int

        do {
            startRow = getRandomInt() % matrix.size
            row = startRow
            do {

                startCol = getRandomInt() % matrix[0].size
                col = startCol
                do {
                    if (isValidPlacement(row, col, currDir, matrix, word)) {
                        placeWordAt(row, col, currDir, matrix, word)
                        return true
                    }
                    col = ++col % matrix[0].size
                } while (col != startCol)
                row = ++row % matrix.size
            } while (row != startRow)
            currDir = currDir.nextDirection()
        } while (currDir !== startDir)
        return false
    }

    private fun isValidPlacement(
            row: Int,
            col: Int,
            dir: Direction,
            matrix: Array<Array<Char>>,
            word: String
    ): Boolean {
        var row = row
        var col = col
        val wLen = word.length
        if (dir === Direction.EAST && col + wLen > matrix[0].size) return false
        if (dir === Direction.WEST && col - wLen < 0) return false
        if (dir === Direction.NORTH && row - wLen < 0) return false
        if (dir === Direction.SOUTH && row + wLen > matrix.size) return false
        if (dir === Direction.SOUTH_EAST && (col + wLen > matrix[0].size || row + wLen > matrix.size)) return false
        if (dir === Direction.NORTH_WEST && (col - wLen < 0 || row - wLen < 0)) return false
        if (dir === Direction.SOUTH_WEST && (col - wLen < 0 || row + wLen > matrix.size)) return false
        if (dir === Direction.NORTH_EAST && (col + wLen > matrix[0].size || row - wLen < 0)) return false
        for (i in 0 until wLen) {
            if (matrix[row][col] != EMPTY_CHAR && matrix[row][col] != word[i]) return false
            col += dir.xOffset
            row += dir.yOffset
        }
        return true
    }

    /**
     * Letakan word pada posisi awal row, col dengan arah dir pada grid array.
     */
    private fun placeWordAt(
            rowIndex: Int,
            colIndex: Int,
            dir: Direction,
            matrix: Array<Array<Char>>,
            word: String
    ) {
        var row = rowIndex
        var col = colIndex
        for (element in word) {
            matrix[row][col] = element
            col += dir.xOffset
            row += dir.yOffset
        }
    }

    data class Input(
            val rowCount: Int,
            val columnCount: Int
    )

    companion object {

        private const val EMPTY_CHAR = '\u0000'
    }
}