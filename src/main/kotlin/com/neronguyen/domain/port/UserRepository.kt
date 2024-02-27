package com.neronguyen.domain.port

import com.neronguyen.domain.model.User
import com.neronguyen.domain.model.UserMessage
import org.bson.types.ObjectId

interface UserRepository {

    suspend fun upsertOne(user: User): Long

    suspend fun findAll(): List<User>

    suspend fun insertUserMessage(objectId: ObjectId, userMessage: UserMessage)
}
