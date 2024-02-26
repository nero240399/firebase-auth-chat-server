package com.neronguyen.application.plugins

import com.neronguyen.application.routes.chatRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(Routing) {
        chatRoute()
    }
}
