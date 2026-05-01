package com.example.whatsapp.core.data.model

import com.google.firebase.Timestamp

data class User(
    val uid: String = "",
    val phoneNumber: String = "",
    val name: String = "",
    val photoUrl: String = "",
    val message: String,
    val timeStamp: Timestamp,
)