package com.thirtyeight.thirtyeight.util

import java.util.concurrent.atomic.AtomicLong

object GenerateId {

    private val nextGeneratedRequestCode = AtomicLong(1L)

    operator fun invoke(): Long {
        while (true) {
            val result = nextGeneratedRequestCode.get()
            var newValue = result + 1
            if (newValue > Long.MAX_VALUE / 2) newValue = 1
            if (nextGeneratedRequestCode.compareAndSet(result, newValue)) {
                return result
            }
        }
    }
}