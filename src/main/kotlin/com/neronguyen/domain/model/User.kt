package com.neronguyen.domain.model

import io.ktor.server.auth.*

data class User(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String
): Principal
