package com.nssus.ihandy.data.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object DialogEventManager {
    private val _dialogEvents = MutableSharedFlow<String>()
    val dialogEvents: SharedFlow<String> = _dialogEvents

    suspend fun emitDialogEvent(message: String) {
        _dialogEvents.emit(message)
    }
}