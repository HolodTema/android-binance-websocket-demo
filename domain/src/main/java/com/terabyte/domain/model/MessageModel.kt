package com.terabyte.domain.model

import java.util.Date

data class MessageModel(
    val text: String,
    val timestamp: Date = Date()
)
