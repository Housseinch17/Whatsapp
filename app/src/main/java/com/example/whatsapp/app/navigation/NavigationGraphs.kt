package com.example.whatsapp.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationGraphs {
    @Serializable
    data object AuthGraph: NavigationGraphs

    @Serializable
    data object ChatGraph: NavigationGraphs
}