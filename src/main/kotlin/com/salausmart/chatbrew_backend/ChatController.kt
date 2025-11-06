package com.salausmart.chatbrew_backend

import com.salausmart.chatbrew_backend.dtos.ChatRequest
import com.salausmart.chatbrew_backend.dtos.ChatResponse
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/chat")
class ChatController(
    private val chatService: ChatService
) {


    @GetMapping()
    fun chat(
        @RequestBody request : ChatRequest
    ) : ResponseEntity<*> {
        val chatResponse = chatService.sendMessage(request) ?: ChatResponse(request.conversationId, "No response") //@Todo remove the null response
        return ResponseEntity.status(HttpStatus.OK).body(chatResponse)
    }
}