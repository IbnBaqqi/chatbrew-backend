package com.salausmart.chatbrew_backend

import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.ChatMemoryRepository
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository
import org.springframework.ai.chat.memory.MessageWindowChatMemory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    //return InMemoryChatRepository implementation of ChatMemory whenever ChatMemory is needed
    @Bean
    fun chatMemoryRepository(): ChatMemoryRepository = InMemoryChatMemoryRepository()

    @Bean
    fun chatMemory(): ChatMemory =
        MessageWindowChatMemory.builder()
            .maxMessages(20)
            .chatMemoryRepository(chatMemoryRepository())
            .build()
}