package com.b1nd.alimo.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.presentation.utiles.Event
import com.b1nd.alimo.presentation.utiles.MutableEventFlow
import com.b1nd.alimo.presentation.utiles.asEventFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun viewEvent(content: String) =
        viewModelScope.launch(Dispatchers.IO) {
            _eventFlow.emit(Event.Success(message = content))
        }

}