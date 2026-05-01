package com.example.whatsapp.core.data

import com.example.whatsapp.core.data.model.Country
import com.example.whatsapp.core.data.model.User
import com.google.firebase.Timestamp
import kotlin.random.Random
import kotlin.random.nextInt

object Constants {
    val country: List<Country> = listOf(
        Country("Lebanon", "+961"),
        Country("France", "+33"),
        Country("Iran", "+98"),
        Country("United State", "+1"),
        Country("Saudi Arabia", "+966"),
        Country("Qatar", "+974")
    )

    val users: List<User> = listOf(
        User(
            "-1",
            phoneNumber = "",
            name = "Andrew Parker",
            photoUrl = "",
            message = "What kind of strategy is better?",
            timeStamp = Timestamp.now()
        ),
        User(
            Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        User(
            Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        User(
            Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        User(
            Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        User(
            Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        User(
            Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        User(
            Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        User(
            Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        User(
            Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        User(
            Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        User(
            Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        User(
            uid = Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        User(
            Random.nextInt(0..1231203940).toString(),
            phoneNumber = "",
            name = "Houssein Chahine",
            photoUrl = "",
            message = "Hello how are you doing today",
            timeStamp = Timestamp.now()
        ),
        )
}