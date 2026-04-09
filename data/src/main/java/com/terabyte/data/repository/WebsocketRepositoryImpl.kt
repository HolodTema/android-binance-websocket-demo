package com.terabyte.data.repository

import com.terabyte.data.storage.WebsocketStorage
import com.terabyte.domain.model.ConnectionStatusModel
import com.terabyte.domain.model.ErrorModel
import com.terabyte.domain.model.MessageModel
import com.terabyte.domain.repository.WebsocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebsocketRepositoryImpl @Inject constructor(
    private val websocketStorage: WebsocketStorage
) : WebsocketRepository {

    override val connectionStatus: StateFlow<ConnectionStatusModel>
        get() = websocketStorage.connectionStatus

    override val messages: SharedFlow<MessageModel>
        get() = websocketStorage.messages

    override val errors: SharedFlow<ErrorModel>
        get() = websocketStorage.errors

    override suspend fun connectWebsocket() {
        websocketStorage.connectWebsocket()
    }

    override suspend fun disconnectWebsocket() {
        websocketStorage.disconnectWebsocket()
    }

    override suspend fun sendMessage(messageModel: MessageModel) {
        websocketStorage.sendMessage(messageModel)
    }
}