package com.rungo.runwithzippy.utils

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

class EventData(var eventCode: EventEnums, var eventPayload: Any?)

enum class EventEnums {
    SUCCESS,
    SUCCESS_LOGIN,
    FAIL,
    OTHER
}