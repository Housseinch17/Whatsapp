package com.example.whatsapp.feature.chat.presentation.data

import com.example.whatsapp.app.navigation.NavigationScreens

object ChatConstants {
    val chatScreensList: List<NavigationScreens> = listOf(
        NavigationScreens.StatusScreen,
        NavigationScreens.CallScreen,
        NavigationScreens.CameraScreen,
        NavigationScreens.ChatScreen,
        NavigationScreens.SettingsScreen
    )
}