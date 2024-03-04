package com.neronguyen.application.routes

import com.google.firebase.messaging.FirebaseMessaging
import com.neronguyen.application.response.toMessage
import com.neronguyen.domain.model.*
import com.neronguyen.domain.port.UserRepository
import com.neronguyen.firebase.FIREBASE_AUTH
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.chatRoute() {

    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
    val userRepository by inject<UserRepository>()

    authenticate(FIREBASE_AUTH) {
        webSocket("chat") {

            val user: User = call.principal() ?: return@webSocket call.respond(HttpStatusCode.Unauthorized)
            val connection = Connection(this)
            connections += connection

            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val text = frame.readText()

                    userRepository.insertUserMessage(user.uid, UserMessage(text))
                    FirebaseMessaging.getInstance().send("[${user.name}] $text".toMessage())

                    connections
                        .filter { it != connection }
                        .forEach { it.session.sendSerialized(user.toMessageResponse(text)) }
                }
            } catch (e: Exception) {
                System.err.println(e.localizedMessage)
            } finally {
                println("Removing $connection!")
                connections -= connection
            }
        }

        get("chatHistory") {
            val users = userRepository.findAll()
            val chatHistory = users.flatMap { user -> user.toMessageResponseList() }
                .sortedBy { message -> message.timestamp }
            call.respond(chatHistory)
        }
    }
}
