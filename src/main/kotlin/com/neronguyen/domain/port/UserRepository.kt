package com.neronguyen.domain.port

import com.neronguyen.domain.model.User
import com.neronguyen.domain.model.UserMessage

interface UserRepository {

    suspend fun upsertOne(user: User): Long

    suspend fun findAll(): List<User>

    suspend fun insertUserMessage(userId: String, userMessage: UserMessage)
}
