package com.thirtyeight.thirtyeight.presentation.logic

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.data.remote.exception.RequestFailedException
import com.thirtyeight.thirtyeight.domain.exception.NoNetworkException
import com.thirtyeight.thirtyeight.util.CoroutineContextProvider
import com.thirtyeight.thirtyeight.util.SingleLifeLiveData
import com.thirtyeight.thirtyeight.util.StringResourceMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by nikolozakhvlediani on 3/20/21.
 */
abstract class BaseViewModel(
        protected val contextPool: CoroutineContextProvider,
        protected val stringResourceMapper: StringResourceMapper
) : ViewModel() {

    private val _simpleErrorLiveData = SingleLifeLiveData<String>()
    val simpleErrorLiveData: LiveData<String> = _simpleErrorLiveData

    @CallSuper
    open fun onCreate() {
    }

    protected fun startTicker(count: Int, delay: Long, block: (tick: Int) -> Unit) {
        viewModelScope.launch(contextPool.iO) {
            val tickerChannel = ticker(delayMillis = delay, initialDelayMillis = 0)
            repeat(count) {
                tickerChannel.receive()
                withContext(Dispatchers.Main) {
                    block(it)
                }
            }
        }
    }

    protected fun postSimpleError(text: String) {
        _simpleErrorLiveData.postValue(text)
    }

    private fun postSimpleError(resId: Int) {
        postSimpleError(stringResourceMapper.getString(resId))
    }

    protected fun postSimpleError(throwable: Throwable) {
        when (throwable) {
            is NoNetworkException -> {
                postSimpleError(R.string.no_network)
            }
            is RequestFailedException -> {
                postSimpleError(throwable.messageText ?: stringResourceMapper.getString(R.string.error_occurred))
            }
            else -> {
                postSimpleError(R.string.error_occurred)
            }
        }
    }
}