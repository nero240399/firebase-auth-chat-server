package com.neronguyen.plugins

import com.neronguyen.model.User
import com.neronguyen.firebase.firebase
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureFirebaseAuth() {
    install(Authentication) {
        firebase {
            validate {
                // TODO look up user profile from DB
                User(it.uid, it.name.orEmpty())
            }
        }
    }
}
