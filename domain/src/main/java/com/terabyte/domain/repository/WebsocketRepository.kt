package com.terabyte.domain.repository

import com.terabyte.domain.model.ConnectionStatusModel
import com.terabyte.domain.model.ErrorModel
import com.terabyte.domain.model.MessageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WebsocketRepository {

    val connectionStatus: StateFlow<ConnectionStatusModel>

    suspend fun connectWebsocket()

    suspend fun disconnectWebsocket()

    fun observeMessages(): Flow<MessageModel>

    fun observeErrors(): Flow<ErrorModel>

}