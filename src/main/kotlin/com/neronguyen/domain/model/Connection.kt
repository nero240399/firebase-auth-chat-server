package com.neronguyen.domain.model

import io.ktor.server.websocket.*

class Connection(
    val session: WebSocketServerSession
)
