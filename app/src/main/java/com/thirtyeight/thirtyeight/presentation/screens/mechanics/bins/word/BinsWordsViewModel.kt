package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.word

import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinsDataEntity
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.bins.GetWordBinsDataUseCase
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.BinsViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
class BinsWordsViewModel(
        data: BinsDataEntity<String>,
        private val getWordBinsDataUseCase: GetWordBinsDataUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : BinsViewModel<String>(data, coroutineContextProvider, stringResourceMapper)