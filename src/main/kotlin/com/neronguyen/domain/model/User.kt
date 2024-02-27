package com.neronguyen.domain.model

import com.neronguyen.application.response.MessageResponse
import io.ktor.server.auth.*
import kotlinx.datetime.toKotlinInstant
import java.time.Instant

data class User(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String,
    val messages: List<UserMessage> = emptyList()
) : Principal

data class UserMessage(
    val content: String,
    val timestamp: Instant = Instant.now()
)

fun User.toMessageResponseList(): List<MessageResponse> = this.messages.map {
    MessageResponse(
        userId = id,
        username = name,
        email = email,
        photoUrl = photoUrl,
        content = it.content,
        timestamp = it.timestamp.toKotlinInstant()
    )
}
