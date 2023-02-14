package ru.kpfu.itis.gnt.fakestore.presentation.redux

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ArrayBlockingQueue

class Store<T>(initialState: T) {

    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow:StateFlow<T> = _stateFlow

    private val mutex = Mutex()

    suspend fun update(updateBlock: (T) -> T) = mutex.withLock {
        val newState = updateBlock(_stateFlow.value)
        _stateFlow.value = newState
    }

    suspend fun <B> read(readBlock: (T)->B) = mutex.withLock {
        readBlock(_stateFlow.value)
    }
}
