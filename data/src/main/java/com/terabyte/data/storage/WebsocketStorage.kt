package com.terabyte.data.storage

import com.terabyte.domain.model.ConnectionStatusModel
import com.terabyte.domain.model.ErrorModel
import com.terabyte.domain.model.MessageModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface WebsocketStorage {

    val connectionStatus: StateFlow<ConnectionStatusModel>

    val messages: SharedFlow<MessageModel>

    val errors: SharedFlow<ErrorModel>

    suspend fun connectWebsocket()

    suspend fun disconnectWebsocket()

    suspend fun sendMessage(messageModel: MessageModel)
}