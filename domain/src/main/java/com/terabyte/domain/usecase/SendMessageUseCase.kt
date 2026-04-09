package com.terabyte.domain.usecase

import com.terabyte.domain.model.MessageModel
import com.terabyte.domain.repository.WebsocketRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val websocketRepository: WebsocketRepository
) {

    suspend operator fun invoke(text: String) {
        val messageModel = MessageModel(text)
        websocketRepository.sendMessage(messageModel)
    }
}