package com.neronguyen.domain.port

import com.neronguyen.domain.model.User

interface UserRepository {

    suspend fun upsertOne(user: User): Long
}
