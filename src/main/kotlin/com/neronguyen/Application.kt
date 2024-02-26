package com.neronguyen

import com.neronguyen.application.plugins.*
import com.neronguyen.firebase.FirebaseAdmin
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    FirebaseAdmin.init()
    configureSerialization()
    configureFirebaseAuth()
    configureWebSocket()
    configureDI()
    configureRouting()
}
