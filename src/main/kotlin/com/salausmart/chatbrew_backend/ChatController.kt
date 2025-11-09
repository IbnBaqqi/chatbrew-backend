package com.salausmart.chatbrew_backend

import com.salausmart.chatbrew_backend.dtos.ChatRequest
import com.salausmart.chatbrew_backend.dtos.ChatResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatController(
    private val chatService: ChatService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping()
    fun chat(
        @RequestBody request : ChatRequest
    ) : ResponseEntity<*> {
        logger.info("Got called")
        val chatResponse = chatService.sendMessage(request) ?: ChatResponse("No response") //@Todo remove the null response
        return ResponseEntity.status(HttpStatus.OK).body(ChatResponse(chatResponse as String))
    }
}