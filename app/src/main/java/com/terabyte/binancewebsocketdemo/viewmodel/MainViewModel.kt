package com.terabyte.binancewebsocketdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terabyte.domain.model.ConnectionStatusModel
import com.terabyte.domain.model.MessageModel
import com.terabyte.domain.usecase.ConnectWebsocketUseCase
import com.terabyte.domain.usecase.DisconnectWebsocketUseCase
import com.terabyte.domain.usecase.GetConnectionStatusUseCase
import com.terabyte.domain.usecase.GetMessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val connectWebsocketUseCase: ConnectWebsocketUseCase,
    private val disconnectWebsocketUseCase: DisconnectWebsocketUseCase,
    private val getConnectionStatusUseCase: GetConnectionStatusUseCase,
    private val getMessagesUseCase: GetMessagesUseCase
) : ViewModel() {

    val stateFlowConnectionStatus: StateFlow<ConnectionStatusModel> = getConnectionStatusUseCase()

    val sharedFlowMessages: SharedFlow<MessageModel> = getMessagesUseCase()

    fun connectWebsocket() {
        viewModelScope.launch(Dispatchers.IO) {
            connectWebsocketUseCase()
        }
    }

    fun disconnectWebsocket() {
        viewModelScope.launch(Dispatchers.IO) {
            disconnectWebsocketUseCase()
        }
    }
}