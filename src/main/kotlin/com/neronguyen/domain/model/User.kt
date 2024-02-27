package com.neronguyen.domain.model

import io.ktor.server.auth.*
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
