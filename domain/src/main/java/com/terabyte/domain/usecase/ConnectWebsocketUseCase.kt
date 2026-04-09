package com.terabyte.domain.usecase

import com.terabyte.domain.repository.WebsocketRepository
import javax.inject.Inject

class ConnectWebsocketUseCase @Inject constructor(
    private val websocketRepository: WebsocketRepository
) {

    suspend operator fun invoke() {
        websocketRepository.connectWebsocket()
    }

}