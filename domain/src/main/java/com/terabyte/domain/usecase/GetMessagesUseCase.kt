package com.terabyte.domain.usecase

import com.terabyte.domain.model.MessageModel
import com.terabyte.domain.repository.WebsocketRepository
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val websocketRepository: WebsocketRepository
) {

    operator fun invoke(): SharedFlow<MessageModel> {
        return websocketRepository.messages
    }
}