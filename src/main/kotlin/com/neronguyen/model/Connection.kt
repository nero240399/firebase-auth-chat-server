package com.neronguyen.model

import io.ktor.websocket.*

class Connection(
    val session: DefaultWebSocketSession,
    val user: User
)
