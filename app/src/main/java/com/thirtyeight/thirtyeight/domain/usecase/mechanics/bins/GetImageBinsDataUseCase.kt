package com.thirtyeight.thirtyeight.domain.usecase.mechanics.bins

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinCategoryEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinFallingItemEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinsDataEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
class GetImageBinsDataUseCase : BaseUseCase<Unit, BinsDataEntity<Int>>() {

    override fun execute(input: Unit): BinsDataEntity<Int> {
        val butterflyCategory = BinCategoryEntity(GenerateId(), "Butterfly")
        val bullCategory = BinCategoryEntity(GenerateId(), "Bull")
        val frogCategory = BinCategoryEntity(GenerateId(), "Frog")
        return BinsDataEntity(
                R.string.sort_falling_objects_in_correct_categories,
                listOf(butterflyCategory, bullCategory, frogCategory),
                listOf(
                        BinFallingItemEntity(GenerateId(), butterflyCategory.id, R.drawable.butterfly),
                        BinFallingItemEntity(GenerateId(), butterflyCategory.id, R.drawable.butterfly),
                        BinFallingItemEntity(GenerateId(), bullCategory.id, R.drawable.cow),
                        BinFallingItemEntity(GenerateId(), bullCategory.id, R.drawable.cow),
                        BinFallingItemEntity(GenerateId(), frogCategory.id, R.drawable.frog),
                        BinFallingItemEntity(GenerateId(), frogCategory.id, R.drawable.frog)
                ).shuffled()
        )
    }
}