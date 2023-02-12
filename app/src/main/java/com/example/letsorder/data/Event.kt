package com.example.letsorder.data

import java.util.concurrent.atomic.AtomicBoolean

class Event<T>(private val state: T) {
    private val isHandled = AtomicBoolean(false)

    fun handle(): T? = if (isHandled.getAndSet(true))
        null
    else
        state
}