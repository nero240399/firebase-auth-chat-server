package com.neronguyen.domain.model

import com.neronguyen.application.response.MessageResponse
import com.neronguyen.application.response.SenderInfo
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
        content = it.content,
        timestamp = it.timestamp.toKotlinInstant(),
        senderInfo = SenderInfo(
            uid = uid,
            name = name,
            email = email,
            photoUrl = photoUrl,
        )
    )
}

fun User.toMessageResponse(message: String) = MessageResponse(
    content = message,
    senderInfo = SenderInfo(
        uid = uid,
        name = name,
        email = email,
        photoUrl = photoUrl,
    )
)
