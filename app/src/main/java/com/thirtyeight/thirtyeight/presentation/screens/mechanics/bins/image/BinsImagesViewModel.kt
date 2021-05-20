package com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.image

import com.thirtyeight.thirtyeight.domain.entities.mechanics.bins.BinsDataEntity
import com.thirtyeight.thirtyeight.domain.usecase.mechanics.bins.GetImageBinsDataUseCase
import com.thirtyeight.thirtyeight.presentation.screens.mechanics.bins.BinsViewModel
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.StringResourceMapper

/**
 * Created by nikolozakhvlediani on 3/29/21.
 */
class BinsImagesViewModel(
        data: BinsDataEntity<Int>,
        private val getImageBinsDataUseCase: GetImageBinsDataUseCase,
        coroutineContextProvider: CoroutineContextProvider,
        stringResourceMapper: StringResourceMapper
) : BinsViewModel<Int>(data, coroutineContextProvider, stringResourceMapper)