package com.salausmart.chatbrew_backend.dtos

import java.util.UUID

data class ChatResponse(
    val conversationId: UUID?,
    val response: String
)
