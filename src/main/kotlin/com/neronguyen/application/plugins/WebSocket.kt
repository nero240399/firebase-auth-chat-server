package com.neronguyen.application.plugins

import io.ktor.server.application.*
import io.ktor.server.websocket.*

fun Application.configureWebSocket() {
    install(WebSockets)
}
