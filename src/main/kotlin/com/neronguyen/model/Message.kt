package com.neronguyen.model

import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification

fun String.toMessage(): Message = Message.builder()
    .setNotification(
        Notification.builder()
            .setBody(this)
            .build()
    )
    .setTopic("chat")
    .build()