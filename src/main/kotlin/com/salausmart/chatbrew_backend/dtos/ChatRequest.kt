package com.salausmart.chatbrew_backend.dtos

import java.util.UUID

data class ChatRequest(
    val conversationId: UUID,
    val prompt: String
)
