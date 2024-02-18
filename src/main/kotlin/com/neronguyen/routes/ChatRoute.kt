package com.neronguyen.routes

import com.neronguyen.firebase.FIREBASE_AUTH
import com.neronguyen.model.Connection
import com.neronguyen.model.User
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.*

fun Route.chatRoute() {
    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
    authenticate(FIREBASE_AUTH) {
        webSocket("chat") {
            val user: User = call.principal() ?: return@webSocket call.respond(HttpStatusCode.Unauthorized)
            val connection = Connection(this, user)
            connections += connection
            try {
                send("${user.name} joined.")
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val text = frame.readText()
                    val textWithUsername = "[${connection.user.name}]: $text"
                    connections.forEach {
                        it.session.send(textWithUsername)
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                println("Removing $connection!")
                connections -= connection
            }
        }
    }
}