package com.thirtyeight.thirtyeight.data.remote.exception

/**
 * Created by nikolozakhvlediani on 4/20/21.
 */
class RequestFailedException(val code: Int, val messageText: String?) : Exception()
