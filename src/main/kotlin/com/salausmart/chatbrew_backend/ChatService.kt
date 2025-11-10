package com.salausmart.chatbrew_backend

import com.salausmart.chatbrew_backend.dtos.ChatRequest
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.ChatMemoryRepository
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.stereotype.Service

@Service
class ChatService (
    chatClientBuilder: ChatClient.Builder,
    chatMemoryRepository: ChatMemoryRepository,
    chatMemory: ChatMemory
){
    private val chatRepository = chatMemoryRepository
    private val chatClient = chatClientBuilder
        .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
        .build()
    private val logger = LoggerFactory.getLogger(this::class.java)

    //@Todo remove the null response
    fun sendMessage(
        request : ChatRequest
    ) : String? {

//        private val memory = ChatMemory.CONVERSATION_ID

        val promptTemplate = PromptTemplate(
            """
                {query}.
                """.trimIndent()
        )

        val prompt: Prompt = promptTemplate.create(mapOf("query" to request.prompt))

        val start = System.currentTimeMillis() // Testing openai speed
        val response = chatClient.prompt(prompt)
            .advisors()
            .call()
            .content()
        logger.info("OpenAI call took ${System.currentTimeMillis() - start} ms") // Testing openai speed
        return response
    }
}