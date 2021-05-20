package com.thirtyeight.thirtyeight.domain.entities.mechanics.gap.sentence

import java.io.Serializable

/**
 * Created by nikolozakhvlediani on 3/27/21.
 */
sealed class SentenceGapItem : Serializable {

    class Word(val text: String) : SentenceGapItem()
    object Gap : SentenceGapItem()
}