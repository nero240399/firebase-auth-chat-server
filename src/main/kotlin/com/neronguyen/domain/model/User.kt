package com.neronguyen.domain.model

import com.neronguyen.application.response.MessageResponse
import io.ktor.server.auth.*
import kotlinx.datetime.toKotlinInstant
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant

data class User(
    @BsonId
    val id: ObjectId = ObjectId(),
    val uid: String,
    val name: String,
    val email: String,
    val photoUrl: String,
    val messages: List<UserMessage> = emptyList()
) : Principal

data class UserMessage(
    val content: String,
    val timestamp: Instant = Instant.now()
)

fun User.toMessageResponseList(): List<MessageResponse> = this.messages.map {
    MessageResponse(
        uid = uid,
        username = name,
        email = email,
        photoUrl = photoUrl,
        content = it.content,
        timestamp = it.timestamp.toKotlinInstant()
    )
}

fun User.toMessageResponse(message: String) = MessageResponse(
    uid = uid,
    username = name,
    email = email,
    photoUrl = photoUrl,
    content = message,
)
