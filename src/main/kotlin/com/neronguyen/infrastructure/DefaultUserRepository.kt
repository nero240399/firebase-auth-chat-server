package com.neronguyen.infrastructure

import com.mongodb.MongoException
import com.mongodb.client.model.*
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.neronguyen.domain.model.User
import com.neronguyen.domain.model.UserMessage
import com.neronguyen.domain.port.UserRepository
import kotlinx.coroutines.flow.toList
import org.bson.codecs.configuration.CodecConfigurationException


class DefaultUserRepository(
    private val mongoDatabase: MongoDatabase
) : UserRepository {

    companion object {
        const val USER_COLLECTION = "user"
    }

    override suspend fun upsertOne(user: User): Long {
        try {
            val filter = Filters.eq(User::id.name, user.id)
            val updates = Updates.combine(
                Updates.set(User::id.name, user.id),
                Updates.set(User::name.name, user.name),
                Updates.set(User::email.name, user.email),
                Updates.set(User::photoUrl.name, user.photoUrl)
            )
            val options = UpdateOptions().upsert(true)
            val result = mongoDatabase.getCollection<User>(USER_COLLECTION).updateOne(filter, updates, options)
            return result.modifiedCount
        } catch (e: MongoException) {
            System.err.println("Unable to upsertOne user due to an error: $e")
        }

        return 0
    }

    override suspend fun findAll(): List<User> {
        return try {
            mongoDatabase.getCollection<User>(USER_COLLECTION).find().toList()
        } catch (e: CodecConfigurationException) {
            System.err.println("Unable to findAll users due to an error: $e")
            emptyList()
        }
    }

    override suspend fun insertUserMessage(userId: String, userMessage: UserMessage) {
        try {
            val filter = Filters.eq(User::id.name, userId)
            val updates = Updates.push(User::messages.name, userMessage)
            val options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
            mongoDatabase.getCollection<User>(USER_COLLECTION).findOneAndUpdate(filter, updates, options)
        } catch (e: MongoException) {
            System.err.println("Unable to insertUserMessage due to an error: $e")
        }
    }
}
