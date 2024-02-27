package com.neronguyen.application.response

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val uid: String,
    val username: String,
    val email: String,
    val photoUrl: String,
    val content: String,
    val timestamp: Instant = Clock.System.now()
)
