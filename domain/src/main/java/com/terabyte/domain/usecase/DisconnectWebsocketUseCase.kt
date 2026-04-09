package com.terabyte.domain.usecase

import com.terabyte.domain.repository.WebsocketRepository
import javax.inject.Inject

class DisconnectWebsocketUseCase @Inject constructor(
    private val websocketRepository: WebsocketRepository
) {

    suspend operator fun invoke() {
        websocketRepository.disconnectWebsocket()
    }

}