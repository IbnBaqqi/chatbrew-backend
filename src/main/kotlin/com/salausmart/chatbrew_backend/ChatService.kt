package com.salausmart.chatbrew_backend

import com.salausmart.chatbrew_backend.dtos.ChatRequest
import com.salausmart.chatbrew_backend.dtos.ChatResponse
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.stereotype.Service

@Service
class ChatService (
    chatClientBuilder: ChatClient.Builder,
    chatMemory: ChatMemory
){
    private val chatClient = chatClientBuilder
        .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
        .build()
    private val logger = LoggerFactory.getLogger(this::class.java)

    //@Todo remove the null response
    fun sendMessage(
        request : ChatRequest
    ) : ChatResponse? {

        val chatId = request.conversationId

        val promptTemplate = PromptTemplate(
            """
                {query}.
                """.trimIndent()
        )

        val prompt: Prompt = promptTemplate.create(mapOf("query" to request.prompt))

//        val start = System.currentTimeMillis() // Testing openai speed
        val response = chatClient.prompt(prompt)
            .advisors { it.param(ChatMemory.CONVERSATION_ID, chatId) }
            .call()
            .entity(ChatResponse::class.java)
//        logger.info("OpenAI call took ${System.currentTimeMillis() - start} ms") // Testing openai speed
        return response
    }
}