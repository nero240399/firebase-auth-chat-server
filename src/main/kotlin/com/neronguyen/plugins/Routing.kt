package com.neronguyen.plugins

import com.neronguyen.routes.chatRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(Routing) {
        chatRoute()
    }
}
