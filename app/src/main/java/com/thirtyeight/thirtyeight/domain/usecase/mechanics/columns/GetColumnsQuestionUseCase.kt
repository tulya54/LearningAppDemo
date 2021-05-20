package com.thirtyeight.thirtyeight.domain.usecase.mechanics.columns

import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.domain.entities.mechanics.columns.ColumnOptionEntity
import com.thirtyeight.thirtyeight.domain.entities.mechanics.columns.ColumnsQuestionEntity
import com.thirtyeight.thirtyeight.domain.usecase.base.BaseUseCase
import com.thirtyeight.thirtyeight.util.GenerateId

/**
 * Created by nikolozakhvlediani on 4/2/21.
 */
class GetColumnsQuestionUseCase : BaseUseCase<Unit, ColumnsQuestionEntity>() {

    override fun execute(input: Unit): ColumnsQuestionEntity {
        val tokyoId = GenerateId()
        val japanId = GenerateId()
        val newYorkId = GenerateId()
        val usaId = GenerateId()
        val londonId = GenerateId()
        val englandId = GenerateId()
        val berlinId = GenerateId()
        val germanyId = GenerateId()
        val parisId = GenerateId()
        val franceId = GenerateId()
        return ColumnsQuestionEntity(
                R.string.tap_the_matching_pairs,
                "Connect a city with a country.",
                listOf(
                        ColumnOptionEntity(tokyoId, "Tokyo"),
                        ColumnOptionEntity(newYorkId, "New York"),
                        ColumnOptionEntity(londonId, "London"),
                        ColumnOptionEntity(berlinId, "Berlin"),
                        ColumnOptionEntity(parisId, "Paris"),
                ).shuffled(),
                listOf(
                        ColumnOptionEntity(japanId, "Japan"),
                        ColumnOptionEntity(usaId, "USA"),
                        ColumnOptionEntity(englandId, "England"),
                        ColumnOptionEntity(germanyId, "Germany"),
                        ColumnOptionEntity(franceId, "France"),
                ).shuffled(),
                listOf(
                        tokyoId to japanId,
                        newYorkId to usaId,
                        londonId to englandId,
                        berlinId to germanyId,
                        parisId to franceId
                )
        )
    }
}