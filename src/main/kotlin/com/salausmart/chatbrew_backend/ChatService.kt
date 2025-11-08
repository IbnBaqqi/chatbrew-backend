package com.salausmart.chatbrew_backend

import com.salausmart.chatbrew_backend.dtos.ChatRequest
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.stereotype.Service

@Service
class ChatService (
    chatClientBuilder: ChatClient.Builder
){
    private val chatClient = chatClientBuilder.build()

    //@Todo remove the null response
    fun sendMessage(
        request : ChatRequest
    ) : String? {
        val promptTemplate = PromptTemplate(
            """
                {query}.
                """.trimIndent()
        )

        val prompt: Prompt = promptTemplate.create(mapOf("query" to request.prompt))


        val response = chatClient.prompt(prompt)
            .call()
            .content()

        return response
    }
}