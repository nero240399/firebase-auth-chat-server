val firebase_admin_version: String by project
val koin_version: String by project
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val mongodb_version: String by project

plugins {
    kotlin("jvm") version "1.9.22"
    application
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}

group = "com.neronguyen"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-websockets:$ktor_version")

    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

    implementation("com.google.firebase:firebase-admin:$firebase_admin_version")

    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:$mongodb_version")

    implementation("io.insert-koin:koin-ktor:$koin_version")
}
