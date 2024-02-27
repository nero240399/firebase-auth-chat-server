package com.neronguyen.application.response

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val userId: String,
    val username: String,
    val email: String,
    val photoUrl: String,
    val content: String,
    val timestamp: Instant
)
