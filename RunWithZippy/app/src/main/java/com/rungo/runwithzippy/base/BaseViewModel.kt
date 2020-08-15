package com.rungo.runwithzippy.base

import androidx.lifecycle.*
import com.rungo.runwithzippy.utils.Event
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import kotlinx.coroutines.*
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    val scope = CoroutineScope(
        Job() + Dispatchers.Main
    )

    val events = MutableLiveData<Event<EventData>>()

    fun sendEvent(code: EventEnums, payload: Any? = null) {
        val eventData = EventData(
            eventCode = code,
            eventPayload = payload
        )
        //must be used setValue instead of postValue. setValue uses event queue
        viewModelScope.launch { events.value = Event(eventData) }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}