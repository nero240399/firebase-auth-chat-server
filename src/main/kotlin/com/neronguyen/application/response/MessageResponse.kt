package com.neronguyen.application.response

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val content: String,
    val timestamp: Instant = Clock.System.now(),
    val senderInfo: SenderInfo
)

@Serializable
data class SenderInfo(
    val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String,
)
