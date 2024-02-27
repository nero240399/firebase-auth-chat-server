package com.neronguyen.application.plugins

import com.neronguyen.domain.model.User
import com.neronguyen.domain.port.UserRepository
import com.neronguyen.firebase.firebase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.koin.ktor.ext.inject

fun Application.configureFirebaseAuth() {

    val userRepository by inject<UserRepository>()

    install(Authentication) {
        firebase {
            validate { token ->
                val user = User(
                    uid = token.uid,
                    name = token.name,
                    email = token.email,
                    photoUrl = token.picture
                )
                userRepository.upsertOne(user)
                user
            }
        }
    }
}
