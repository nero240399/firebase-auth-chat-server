package com.neronguyen.application.plugins

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.neronguyen.domain.port.UserRepository
import com.neronguyen.infrastructure.DefaultUserRepository
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureDI() {
    install(Koin) {
        modules(module {
            single {
                MongoClient.create(
                    environment.config.propertyOrNull("ktor.mongo.uri")?.getString()
                        ?: throw RuntimeException("Failed to access MongoDB URI.")
                )
            }
            single {
                get<MongoClient>().getDatabase(
                    environment.config.property("ktor.mongo.database").getString()
                )
            }
        }, module {
            single<UserRepository> { DefaultUserRepository(get()) }
        })
    }
}
