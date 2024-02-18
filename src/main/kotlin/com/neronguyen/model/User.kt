package com.neronguyen.model

import io.ktor.server.auth.*

data class User(
    val id: String,
    val name: String
): Principal