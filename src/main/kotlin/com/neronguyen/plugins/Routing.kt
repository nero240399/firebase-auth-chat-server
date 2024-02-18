package com.neronguyen.plugins

import com.neronguyen.firebase.FIREBASE_AUTH
import com.neronguyen.model.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        authenticate(FIREBASE_AUTH) {
            get("/authenticated") {
                val user: User = call.principal() ?: return@get call.respond(HttpStatusCode.Unauthorized)
                call.respond("User is authenticated: $user")
            }
        }
    }
}
