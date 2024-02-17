package com.neronguyen.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.InputStream

object FirebaseAdmin {
    private val serviceAccountStream =
        this::class.java.classLoader.getResourceAsStream("psychic-memory-firebase-adminsdk.json")

    private val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
        .build()

    fun init(): FirebaseApp = FirebaseApp.initializeApp(options)
}