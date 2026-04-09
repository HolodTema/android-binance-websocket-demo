package com.terabyte.data.storage

import android.util.Log
import com.terabyte.domain.model.ConnectionStatusModel
import com.terabyte.domain.model.ErrorModel
import com.terabyte.domain.model.MessageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebsocketStorageImpl @Inject constructor() : WebsocketStorage {

    //we do not provide websocketClient via DI, because of it lives during connection lives
    //and it is nullable
    private var webSocketClient: WebSocketClient? = null

    private val _connectionStatus = MutableStateFlow(ConnectionStatusModel.DISCONNECTED)
    override val connectionStatus: StateFlow<ConnectionStatusModel>
        get() = _connectionStatus.asStateFlow()

    private val _messages = MutableSharedFlow<MessageModel>()
    override val messages: SharedFlow<MessageModel>
        get() = _messages.asSharedFlow()

    private val _errors = MutableSharedFlow<ErrorModel>()
    override val errors: SharedFlow<ErrorModel>
        get() = _errors.asSharedFlow()

    override suspend fun connectWebsocket() {
        try {
            _connectionStatus.value = ConnectionStatusModel.CONNECTING
            val uri = URI("wss://stream.binance.com:9443/ws/btcusdt@trade")

            webSocketClient = object : WebSocketClient(uri) {

                override fun onOpen(handshakeData: ServerHandshake?) {
                    Log.d("mydebug", "websocket connected")
                    _connectionStatus.value = ConnectionStatusModel.CONNECTED
                    this.send("{\\\"method\\\":\\\"SUBSCRIBE\\\",\\\"params\\\":[\\\"btcusdt@trade\\\"],\\\"id\\\":1}\"")
                }

                override fun onMessage(msg: String?) {
                    msg?.let {
                        CoroutineScope(Dispatchers.IO).launch {
                            _messages.emit(MessageModel(msg))
                        }
                    }
                }

                override fun onClose(code: Int, reason: String?, remote: Boolean) {
                    Log.d("mydebug", "websocket disconnected")
                    _connectionStatus.value = ConnectionStatusModel.DISCONNECTED
                }

                override fun onError(e: java.lang.Exception?) {
                    e?.let {
                        Log.d("mydebug", "websocket error: ${e.message ?: "Unknown error"}")
                        CoroutineScope(Dispatchers.IO).launch {
                            val errorModel = ErrorModel(e.message ?: "Unknown error")
                            _errors.emit(errorModel)
                        }
                    }
                }
            }

            CoroutineScope(Dispatchers.IO).launch {
                webSocketClient?.connectBlocking()
            }
        }
        catch (e: Exception) {
            Log.e("mydebug", "Error connecting websocket: ${e.message ?: ""}")
            CoroutineScope(Dispatchers.IO).launch {
                val errorModel = ErrorModel(e.message ?: "Connection failed")
                _errors.emit(errorModel)
            }
        }
    }

    override suspend fun disconnectWebsocket() {
        try {
            webSocketClient?.close()
            webSocketClient = null
            _connectionStatus.value = ConnectionStatusModel.DISCONNECTED
        }
        catch (e: Exception) {
            Log.e("mydebug", "Error disconnecting websocket: ${e.message ?: ""}")
        }
    }

    override suspend fun sendMessage(messageModel: MessageModel) {
        webSocketClient?.send(messageModel.text)
    }
}