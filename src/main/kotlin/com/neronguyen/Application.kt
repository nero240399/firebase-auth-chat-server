package com.neronguyen

import com.neronguyen.firebase.FirebaseAdmin
import com.neronguyen.plugins.configureFirebaseAuth
import com.neronguyen.plugins.configureRouting
import com.neronguyen.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    FirebaseAdmin.init()
    configureSerialization()
    configureFirebaseAuth()
    configureRouting()
}
