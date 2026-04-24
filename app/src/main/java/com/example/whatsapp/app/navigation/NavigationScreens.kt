package com.example.whatsapp.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationScreens {
    @Serializable
    data object AuthenticationScreen: NavigationScreens
}