package com.terabyte.domain.usecase

import com.terabyte.domain.model.ConnectionStatusModel
import com.terabyte.domain.repository.WebsocketRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetConnectionStatusUseCase @Inject constructor(
    private val websocketRepository: WebsocketRepository
) {

    operator fun invoke(): StateFlow<ConnectionStatusModel> {
        return websocketRepository.connectionStatus
    }
}